package com.example.scionic_ai_assignment_kotlin_project.auth.infra.entity

import com.example.scionic_ai_assignment_kotlin_project.auth.domain.model.RoleType
import com.example.scionic_ai_assignment_kotlin_project.common.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.util.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: RoleType
) : BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = Objects.hash(id)
}
