package com.example.scionic_ai_assignment_kotlin_project.chat.presentation.controller

import com.example.scionic_ai_assignment_kotlin_project.chat.application.ChatService
import com.example.scionic_ai_assignment_kotlin_project.chat.presentation.dto.request.CreateChatRequest
import com.example.scionic_ai_assignment_kotlin_project.chat.presentation.dto.response.CreateChatResponse
import com.example.scionic_ai_assignment_kotlin_project.chat.presentation.dto.response.ThreadResponse
import com.example.scionic_ai_assignment_kotlin_project.common.security.model.CustomUserDetails
import jakarta.validation.Valid
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/chat")
@Validated
class ChatController(private val chatService: ChatService) {

    @PostMapping
    fun createChat(
        @Valid @RequestBody request: CreateChatRequest,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ): ResponseEntity<CreateChatResponse> {
        val chat =
            chatService.createChat(
                userId = userDetails.userId,
                request = request,
            )

        val response =
            CreateChatResponse(
                id = chat.id!!,
                userId = chat.user!!.id!!,
                threadId = chat.thread!!.id!!,
                question = chat.question,
                answer = chat.answer,
                createdAt = chat.createdAt
            )

        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun findAllChats(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<Page<ThreadResponse>> {
        val threads = chatService.findAllChats(userDetails.userId, userDetails.isAdmin(), pageable)

        val response =
            threads.map { thread ->
                ThreadResponse(
                    id = thread.id!!,
                    userId = thread.user!!.id!!,
                    chats =
                    thread.chats.map { chat ->
                        com.example.scionic_ai_assignment_kotlin_project.chat
                            .presentation.dto.response.ChatResponse(
                                id = chat.id!!,
                                userId = chat.user!!.id!!,
                                threadId = chat.thread!!.id!!,
                                question = chat.question,
                                answer = chat.answer,
                                createdAt = chat.createdAt
                            )
                    },
                    createdAt = thread.createdAt!!,
                    lastActivityAt = thread.lastActivityAt!!
                )
            }

        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/thread/{threadId}")
    fun deleteThread(
        @PathVariable threadId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ): ResponseEntity<Map<String, String>> {
        val deleted = chatService.deleteThread(threadId, userDetails.userId, userDetails.isAdmin())

        return if (deleted) {
            ResponseEntity.ok(mapOf("message" to "스레드가 삭제되었습니다."))
        } else {
            ResponseEntity.badRequest().body(mapOf("message" to "스레드 삭제에 실패했습니다."))
        }
    }
}
