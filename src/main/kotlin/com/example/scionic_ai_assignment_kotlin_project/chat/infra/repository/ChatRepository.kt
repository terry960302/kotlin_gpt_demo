package com.example.scionic_ai_assignment_kotlin_project.chat.infra.repository

import com.example.scionic_ai_assignment_kotlin_project.chat.infra.entity.ChatEntity
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : JpaRepository<ChatEntity, UUID> {
    fun findByThreadId(threadId: UUID): List<ChatEntity>
    fun findByUserId(userId: UUID): List<ChatEntity>
}
