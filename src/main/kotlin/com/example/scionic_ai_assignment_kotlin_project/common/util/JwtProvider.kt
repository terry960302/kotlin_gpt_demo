package com.example.scionic_ai_assignment_kotlin_project.common.util

import com.example.scionic_ai_assignment_kotlin_project.common.config.JwtProperties
import com.example.scionic_ai_assignment_kotlin_project.common.security.service.CustomUserDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.util.*
import org.springframework.stereotype.Component

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val customUserDetailsService: CustomUserDetailsService
) {

    fun generateToken(userId: String, email: String, role: String): String {
        val now = Date()
        val expiration = Date(now.time + jwtProperties.expiration)

        return Jwts.builder()
            .subject(userId)
            .claim("email", email)
            .claim("role", role)
            .issuedAt(now)
            .expiration(expiration)
            .signWith(
                Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray()),
                SignatureAlgorithm.HS256
            )
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray()))
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUserId(token: String): String {
        return Jwts.parser()
            .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun getAuthentication(token: String): Authentication {
        val userId = getUserId(token)
        val userDetails = customUserDetailsService.loadUserByUsername(userId)

        return UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities
        )
    }
}
