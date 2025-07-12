package com.example.scionic_ai_assignment_kotlin_project

import com.example.scionic_ai_assignment_kotlin_project.common.config.JwtProperties
import com.example.scionic_ai_assignment_kotlin_project.common.config.OpenAIProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class, OpenAIProperties::class)
class ScionicAiAssignmentKotlinProjectApplication

fun main(args: Array<String>) {
    runApplication<ScionicAiAssignmentKotlinProjectApplication>(*args)
}
