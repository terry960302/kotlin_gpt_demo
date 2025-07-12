package com.example.scionic_ai_assignment_kotlin_project.feedback.domain

import java.time.LocalDateTime

data class FeedbackDomain(
    val userId: String,
    val chatId: String,
    val isPositive: Boolean,
    val createdAt: LocalDateTime,
    val status: FeedbackStatus,
) {
}