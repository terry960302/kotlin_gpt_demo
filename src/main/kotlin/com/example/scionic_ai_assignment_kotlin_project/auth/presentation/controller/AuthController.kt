package com.example.scionic_ai_assignment_kotlin_project.auth.presentation.controller

import com.example.scionic_ai_assignment_kotlin_project.auth.application.AuthService
import com.example.scionic_ai_assignment_kotlin_project.auth.presentation.dto.request.LoginRequest
import com.example.scionic_ai_assignment_kotlin_project.auth.presentation.dto.request.SignUpRequest
import com.example.scionic_ai_assignment_kotlin_project.auth.presentation.dto.response.LoginResponse
import com.example.scionic_ai_assignment_kotlin_project.auth.presentation.dto.response.SignUpResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
@Validated
class AuthController(private val authService: AuthService) {

    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody request: SignUpRequest): ResponseEntity<SignUpResponse> {
        val res = authService.signUp(request)
        return ResponseEntity.ok(res)
    }

    @PostMapping("/login")
    fun signIn(@Valid @RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val res = authService.signIn(request)
        return ResponseEntity.ok(res)
    }
}
