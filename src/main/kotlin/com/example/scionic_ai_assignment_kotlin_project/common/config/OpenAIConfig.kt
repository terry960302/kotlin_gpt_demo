package com.example.scionic_ai_assignment_kotlin_project.common.config

// OpenAIConfig.kt
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Configuration
@EnableConfigurationProperties(OpenAIProperties::class)
class OpenAIConfig(
    private val openAIProperties: OpenAIProperties
) {

    @Bean
    fun openAIWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(openAIProperties.baseUrl)
            .defaultHeader("Authorization", "Bearer ${openAIProperties.apiKey}")
            .defaultHeader("Content-Type", "application/json")
            .codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024) // 10MB
            }
            .build()
    }
}
