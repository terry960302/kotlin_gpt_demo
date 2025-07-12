package com.example.scionic_ai_assignment_kotlin_project.chat.infra.entity

import com.example.scionic_ai_assignment_kotlin_project.auth.infra.entity.UserEntity
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*
import org.hibernate.Hibernate

@Entity
@Table(name = "chats")
data class ChatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity? = null,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", nullable = false)
    val thread: ThreadEntity? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    val question: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val answer: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ChatEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = Objects.hash(id)
}
