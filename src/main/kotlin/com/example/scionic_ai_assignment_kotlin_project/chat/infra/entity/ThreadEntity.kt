package com.example.scionic_ai_assignment_kotlin_project.chat.infra.entity

import com.example.scionic_ai_assignment_kotlin_project.auth.infra.entity.UserEntity
import com.example.scionic_ai_assignment_kotlin_project.common.entity.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*
import org.hibernate.Hibernate

@Entity
@Table(name = "threads")
data class ThreadEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity?,

    @OneToMany(fetch = FetchType.LAZY)
    val chats: MutableList<ChatEntity> = mutableListOf(),

    @Column(name = "last_activity_at", nullable = false)
    val lastActivityAt: LocalDateTime
) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ThreadEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = Objects.hash(id)
}
