package com.example.scionic_ai_assignment_kotlin_project.chat.infra.repository

import com.example.scionic_ai_assignment_kotlin_project.chat.infra.entity.ThreadEntity
import java.time.LocalDateTime
import java.util.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ThreadRepository : JpaRepository<ThreadEntity, UUID> {
    fun findByUserId(userId: UUID, pageable: Pageable): Page<ThreadEntity>

    @Query(
            "SELECT t FROM threads t WHERE t.userId = :userId AND t.lastActivityAt > :expirationTime ORDER BY t.lastActivityAt DESC"
    )
    fun findActiveThreadByUserId(
            @Param("userId") userId: UUID,
            @Param("expirationTime") expirationTime: LocalDateTime
    ): ThreadEntity?

    fun existsByUserId(userId: UUID): Boolean
}
