package com.example.scionic_ai_assignment_kotlin_project.chat.application

import com.example.scionic_ai_assignment_kotlin_project.chat.domain.port.ChatRepositoryPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class DeleteChatUseCase(
    private val chatRepository: ChatRepositoryPort,
) {

    fun deleteThreadByMember(threadId: UUID, userId: UUID): Boolean {
        val thread =
            chatRepository.findThreadById(threadId) ?: throw RuntimeException("스레드를 찾을 수 없습니다.")

        val isOwner = thread.user?.id == userId
        if (!isOwner) throw RuntimeException("자신이 생성한 스레드만 삭제할 수 있습니다.")

        return chatRepository.deleteThread(threadId)
    }

    fun deleteThreadByAdmin(threadId: UUID): Boolean {
        chatRepository.findThreadById(threadId) ?: throw RuntimeException("스레드를 찾을 수 없습니다.")
        return chatRepository.deleteThread(threadId)
    }


}