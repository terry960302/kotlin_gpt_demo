package com.example.scionic_ai_assignment_kotlin_project.chat.application

import com.example.scionic_ai_assignment_kotlin_project.auth.domain.model.UserDomain
import com.example.scionic_ai_assignment_kotlin_project.auth.domain.port.UserRepositoryPort
import com.example.scionic_ai_assignment_kotlin_project.auth.infra.repository.UserCrudRepository
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.model.ChatDomain
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.model.ThreadDomain
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.port.ChatRepositoryPort
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.port.GptChatPort
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.vo.ChatMessage
import com.example.scionic_ai_assignment_kotlin_project.chat.presentation.dto.request.CreateChatRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreateChatUseCase(
    private val userRepository: UserRepositoryPort,
    private val chatRepository: ChatRepositoryPort,
    private val gptChat: GptChatPort
) {

    fun createChat(
        userId: UUID,
        request: CreateChatRequest,
    ): ChatDomain {
        // 활성 스레드 찾기 또는 새로 생성
        val thread = getActiveThread(userId)

        // GPT API 호출하여 답변 생성
        val answer = generateAnswer(thread, request.question, request.isStreaming, request.model)


        // 3. 채팅 저장
        val user = userRepository.findById(userId)
        val chat =
            ChatDomain.of(
                user = user,
                thread = thread,
                question = request.question,
                answer = answer
            )
        val savedChat = chatRepository.saveChat(chat)

        // 4. 스레드 업데이트
        val updatedThread = thread.addChat(savedChat)
        chatRepository.saveThread(updatedThread)

        return savedChat
    }


    private fun getActiveThread(userId: UUID): ThreadDomain {
        val activeThread = chatRepository.findActiveThreadByUserId(userId)
        return if (activeThread != null && !activeThread.isExpired()) {
            activeThread
        } else {
            val user = userRepository.findById(userId)
            val newThread = ThreadDomain.of(user)
            chatRepository.saveThread(newThread)
        }

    }

    private fun generateAnswer(thread: ThreadDomain, question: String, isStreaming: Boolean, model: String): String {
        val messages =
            thread.getChatMessages() +
                    ChatMessage(
                        role = "user",
                        content = question
                    )


        return if (isStreaming) {
            gptChat.completeStream(messages, model)
        } else {
            gptChat.complete(messages, model)
        }
    }
}