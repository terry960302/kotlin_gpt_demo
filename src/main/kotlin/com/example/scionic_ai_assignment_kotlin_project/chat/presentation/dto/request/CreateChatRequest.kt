package com.example.scionic_ai_assignment_kotlin_project.chat.presentation.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateChatRequest(
        @field:NotBlank(message = "질문은 필수입니다.")
        @field:Size(max = 1000, message = "질문은 1000자를 초과할 수 없습니다.")
        val question: String,
        val isStreaming: Boolean = false,
        val model: String = "gpt-3.5-turbo"
)
