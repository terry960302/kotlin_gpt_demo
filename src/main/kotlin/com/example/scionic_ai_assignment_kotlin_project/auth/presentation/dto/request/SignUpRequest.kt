package com.example.scionic_ai_assignment_kotlin_project.auth.presentation.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignUpRequest(
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    @field:NotBlank(message = "이메일은 필수입니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수입니다.")
    val password: String,

    @field:NotBlank(message = "이름은 필수입니다.")
    val name: String
)