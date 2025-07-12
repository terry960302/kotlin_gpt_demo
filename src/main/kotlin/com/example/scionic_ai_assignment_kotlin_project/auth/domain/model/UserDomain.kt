package com.example.scionic_ai_assignment_kotlin_project.auth.domain.model

import com.example.scionic_ai_assignment_kotlin_project.auth.infra.entity.UserEntity
import java.time.LocalDateTime
import java.util.UUID

data class UserDomain private constructor(
    val id: UUID? = null,
    val email: String,
    val password: String,
    val name: String,
    val role: RoleType,
    val createdAt: LocalDateTime?,
) {
    init {
        require(email.isNotBlank() && email.contains("@")) { "유효하지 않은 이메일" }
        require(password.length >= 8) { "비밀번호는 최소 8자 이상" }
        require(name.isNotBlank()) { "이름은 필수" }
    }

    fun toEntity(): UserEntity {
        return UserEntity(
            email = email,
            password = password,
            name = name,
            role = role
        )
    }

    companion object {

        fun of(email: String, password: String, name: String, role: RoleType): UserDomain {
            return UserDomain(
                email = email,
                password = password,
                name = name,
                role = role,
                createdAt = null,
            )
        }

        fun fromEntity(entity: UserEntity?): UserDomain? {
            if (entity == null) return null
            return UserDomain(
                email = entity.email,
                password = entity.password,
                name = entity.name,
                role = entity.role,
                createdAt = entity.createdAt
            )
        }
    }
}
