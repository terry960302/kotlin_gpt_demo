package com.example.scionic_ai_assignment_kotlin_project.auth.infra.repository

import com.example.scionic_ai_assignment_kotlin_project.auth.infra.entity.UserEntity
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCrudRepository : JpaRepository<UserEntity, UUID> {
    fun findByEmail(email: String): UserEntity?
    fun existsByEmail(email: String): Boolean
}
