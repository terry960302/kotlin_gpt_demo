package com.example.scionic_ai_assignment_kotlin_project.chat.application

import com.example.scionic_ai_assignment_kotlin_project.chat.domain.model.ChatDomain
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.model.ThreadDomain
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.port.ChatRepositoryPort
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.port.GptChatPort
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.vo.ChatMessage
import com.example.scionic_ai_assignment_kotlin_project.chat.presentation.dto.request.CreateChatRequest
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChatService(
    private val createChatUseCase: CreateChatUseCase,
    private val findChatUseCase: FindChatUseCase,
    private val deleteChatUseCase: DeleteChatUseCase,
) {

    fun createChat(
        userId: UUID,
        request: CreateChatRequest,
    ): ChatDomain {
        return createChatUseCase.createChat(userId, request)
    }

    fun findAllChats(userId: UUID, isAdmin: Boolean, pageable: Pageable): Page<ThreadDomain> {
        return if (isAdmin) {
            findChatUseCase.findAllChatsByAdmin(pageable)
        } else {
            findChatUseCase.findAllChatsByMember(userId, pageable)
        }
    }

    fun deleteThread(threadId: UUID, userId: UUID, isAdmin: Boolean): Boolean {
        return if (isAdmin) {
            deleteChatUseCase.deleteThreadByAdmin(threadId)
        } else {
            deleteChatUseCase.deleteThreadByMember(threadId, userId)
        }
    }
}
