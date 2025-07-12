package com.example.scionic_ai_assignment_kotlin_project.chat.application

import com.example.scionic_ai_assignment_kotlin_project.chat.domain.model.ThreadDomain
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.port.ChatRepositoryPort
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class FindChatUseCase(
    private val chatRepository: ChatRepositoryPort,
) {

    fun findAllChatsByMember(userId: UUID, pageable: Pageable): Page<ThreadDomain> {
        return chatRepository.findThreadsByUserId(userId, pageable)
    }

    fun findAllChatsByAdmin(pageable: Pageable): Page<ThreadDomain> {
        return chatRepository.findAllThreads(pageable)
    }
}