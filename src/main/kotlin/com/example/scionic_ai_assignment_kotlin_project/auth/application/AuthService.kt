package com.example.scionic_ai_assignment_kotlin_project.auth.application

import com.example.scionic_ai_assignment_kotlin_project.common.util.JwtProvider
import com.example.scionic_ai_assignment_kotlin_project.common.util.PasswordEncoder
import com.example.scionic_ai_assignment_kotlin_project.auth.domain.model.UserDomain
import com.example.scionic_ai_assignment_kotlin_project.auth.domain.model.RoleType
import com.example.scionic_ai_assignment_kotlin_project.auth.infra.repository.UserCrudRepository
import com.example.scionic_ai_assignment_kotlin_project.auth.presentation.dto.request.LoginRequest
import com.example.scionic_ai_assignment_kotlin_project.auth.presentation.dto.request.SignUpRequest
import com.example.scionic_ai_assignment_kotlin_project.auth.presentation.dto.response.LoginResponse
import com.example.scionic_ai_assignment_kotlin_project.auth.presentation.dto.response.SignUpResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val userCrudRepository: UserCrudRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider
) {

    fun signUp(request: SignUpRequest): SignUpResponse {
        if (userCrudRepository.existsByEmail(request.email)) {
            throw RuntimeException("이미 존재하는 사용자입니다.")
        }

        val userDomain =
            UserDomain.of(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                name = request.name,
                role =
                RoleType.MEMBER
            )

        val savedEntity = userCrudRepository.save(userDomain.toEntity())
        val savedDomain = UserDomain.fromEntity(savedEntity)

        return SignUpResponse(
            name = savedDomain!!.name,
            email = savedDomain.email,
            role = savedDomain.role,
            createdAt = savedDomain.createdAt,
        )
    }

    fun signIn(request: LoginRequest): LoginResponse {
        val userEntity =
            userCrudRepository.findByEmail(request.email)
                ?: throw RuntimeException("사용자를 찾을 수 없습니다.")

        val userDomain = UserDomain.fromEntity(userEntity)

        // 비밀번호 확인
        if (!passwordEncoder.matches(request.password, userDomain!!.password)) {
            throw RuntimeException("비밀번호가 일치하지 않습니다.")
        }

        // JWT 토큰 생성
        val token =
            jwtProvider.generateToken(
                userId = userEntity.id.toString(),
                email = userDomain.email,
                role = userDomain.role.name
            )

        return LoginResponse(token = token)
    }
}
