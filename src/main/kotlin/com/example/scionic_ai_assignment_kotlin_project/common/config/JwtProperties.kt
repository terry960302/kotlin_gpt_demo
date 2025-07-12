package com.example.scionic_ai_assignment_kotlin_project.common.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app.jwt")
data class JwtProperties(val secret: String, val expiration: Long)
