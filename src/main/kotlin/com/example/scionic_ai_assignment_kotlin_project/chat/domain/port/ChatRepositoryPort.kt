package com.example.scionic_ai_assignment_kotlin_project.chat.domain.port

import com.example.scionic_ai_assignment_kotlin_project.chat.domain.model.ChatDomain
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.model.ThreadDomain
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ChatRepositoryPort {
    fun saveChat(chat: ChatDomain): ChatDomain
    fun saveThread(thread: ThreadDomain): ThreadDomain
    fun findThreadById(threadId: UUID): ThreadDomain?
    fun findActiveThreadByUserId(userId: UUID): ThreadDomain?
    fun findThreadsByUserId(userId: UUID, pageable: Pageable): Page<ThreadDomain>
    fun findAllThreads(pageable: Pageable): Page<ThreadDomain>
    fun deleteThread(threadId: UUID): Boolean
    fun existsByThreadId(threadId: UUID): Boolean
}
