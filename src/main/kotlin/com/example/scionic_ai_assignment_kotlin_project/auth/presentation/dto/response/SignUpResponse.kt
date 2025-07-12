package com.example.scionic_ai_assignment_kotlin_project.auth.presentation.dto.response

import com.example.scionic_ai_assignment_kotlin_project.auth.domain.model.RoleType
import java.time.LocalDateTime

data class SignUpResponse(
    val name: String,
    val email: String,
    val createdAt: LocalDateTime?,
    val role: RoleType,
) {
}