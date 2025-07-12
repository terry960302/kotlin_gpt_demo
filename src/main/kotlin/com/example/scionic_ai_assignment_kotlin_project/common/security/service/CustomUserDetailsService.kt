package com.example.scionic_ai_assignment_kotlin_project.common.security.service

// CustomUserDetailsService.kt
import com.example.scionic_ai_assignment_kotlin_project.auth.infra.repository.UserCrudRepository
import com.example.scionic_ai_assignment_kotlin_project.common.security.model.CustomUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailsService(
    private val userCrudRepository: UserCrudRepository
) : UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val user = userCrudRepository.findById(UUID.fromString(id))
            .orElseThrow { throw UsernameNotFoundException("사용자를 찾을 수 없습니다: $id") }

        return CustomUserDetails(
            userId = user.id!!,
            email = user.email,
            role = user.role.name,
        )
    }
}
