package com.example.scionic_ai_assignment_kotlin_project.common.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoder {
    private val encoder = BCryptPasswordEncoder()

    fun encode(password: String): String {
        return encoder.encode(password)
    }

    fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return encoder.matches(rawPassword, encodedPassword)
    }
}
