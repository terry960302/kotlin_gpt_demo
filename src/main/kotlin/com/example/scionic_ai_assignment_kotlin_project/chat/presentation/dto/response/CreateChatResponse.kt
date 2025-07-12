package com.example.scionic_ai_assignment_kotlin_project.chat.presentation.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class CreateChatResponse(
        val id: UUID,
        val userId: UUID,
        val threadId: UUID,
        val question: String,
        val answer: String,
        val createdAt: LocalDateTime
)
