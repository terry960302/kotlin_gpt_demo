package com.example.scionic_ai_assignment_kotlin_project.common.security.model

// CustomUserDetails.kt
import com.example.scionic_ai_assignment_kotlin_project.auth.domain.model.RoleType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

class CustomUserDetails(
    val userId: UUID,
    private val email: String,
    private val role: String,
) : UserDetails {

    fun isAdmin() : Boolean{
        return role == RoleType.ADMIN.name;
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_$role"))
    }

    override fun getPassword(): String = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = isEnabled
}
