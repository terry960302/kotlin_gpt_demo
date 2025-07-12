package com.example.scionic_ai_assignment_kotlin_project.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "app.openai")
data class OpenAIProperties(
    val apiKey: String,
    val baseUrl: String,
    val model: String,
) {
}