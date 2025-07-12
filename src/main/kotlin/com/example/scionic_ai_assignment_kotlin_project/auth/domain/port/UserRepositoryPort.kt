package com.example.scionic_ai_assignment_kotlin_project.auth.domain.port

import com.example.scionic_ai_assignment_kotlin_project.auth.domain.model.UserDomain
import java.util.UUID

interface UserRepositoryPort {
    fun save(userDomain: UserDomain): UserDomain
    fun findByEmail(email: String): UserDomain
    fun existsByEmail(email: String): Boolean
    fun findById(userId: UUID): UserDomain
    fun deleteById(userId: UUID)
}