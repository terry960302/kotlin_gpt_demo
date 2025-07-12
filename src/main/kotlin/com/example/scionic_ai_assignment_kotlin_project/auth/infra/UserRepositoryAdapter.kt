package com.example.scionic_ai_assignment_kotlin_project.auth.infra

import com.example.scionic_ai_assignment_kotlin_project.auth.domain.port.UserRepositoryPort
import java.util.*

import com.example.scionic_ai_assignment_kotlin_project.auth.domain.model.UserDomain
import com.example.scionic_ai_assignment_kotlin_project.auth.infra.repository.UserCrudRepository
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(
    private val userCrudRepository: UserCrudRepository
) : UserRepositoryPort {

    override fun save(userDomain: UserDomain): UserDomain {
        val entity = userDomain.toEntity()
        val savedEntity = userCrudRepository.save(entity)
        return UserDomain.fromEntity(savedEntity)!!
    }

    override fun findByEmail(email: String): UserDomain {
        val entity = userCrudRepository.findByEmail(email) ?: throw RuntimeException("사용자가 존재하지 않습니다.")
        return UserDomain.fromEntity(entity)!!
    }

    override fun existsByEmail(email: String): Boolean {
        return userCrudRepository.existsByEmail(email)
    }

    override fun findById(userId: UUID): UserDomain {
        val entity = userCrudRepository.findById(userId).orElse(null) ?: throw RuntimeException("사용자가 존재하지 않습니다.")
        return UserDomain.fromEntity(entity)!!
    }

    override fun deleteById(userId: UUID) {
        userCrudRepository.deleteById(userId)
    }
}
