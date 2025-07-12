package com.example.scionic_ai_assignment_kotlin_project.chat.domain.port

import com.example.scionic_ai_assignment_kotlin_project.chat.domain.vo.ChatMessage


interface GptChatPort {
    fun complete(messages: List<ChatMessage>, model: String = "gpt-3.5-turbo"): String
    fun completeStream(messages: List<ChatMessage>, model: String = "gpt-3.5-turbo"): String
}
