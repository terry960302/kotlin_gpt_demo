package com.example.scionic_ai_assignment_kotlin_project.chat.domain.model

import com.example.scionic_ai_assignment_kotlin_project.auth.domain.model.UserDomain
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.vo.ChatMessage
import com.example.scionic_ai_assignment_kotlin_project.chat.infra.entity.ThreadEntity
import java.time.LocalDateTime
import java.util.UUID

data class ThreadDomain
private constructor(
    val id: UUID? = null,
    val user: UserDomain?,
    val chats: List<ChatDomain>,
    val createdAt: LocalDateTime?,
    val lastActivityAt: LocalDateTime?,
) {

    fun addChat(chat: ChatDomain): ThreadDomain {
        return copy(chats = chats + chat, lastActivityAt = chat.createdAt)
    }

    fun isExpired(expirationMinutes: Long = 30): Boolean {
        return LocalDateTime.now().isAfter(lastActivityAt?.plusMinutes(expirationMinutes))
    }

    fun getChatMessages(): List<ChatMessage> {
        return chats.flatMap { chat ->
            listOf(
                ChatMessage(role = "user", content = chat.question),
                ChatMessage(role = "assistant", content = chat.answer)
            )
        }
    }

    companion object {
        fun of(user: UserDomain): ThreadDomain {
            return ThreadDomain(
                user = user,
                chats = emptyList(),
                createdAt = LocalDateTime.now(),
                lastActivityAt = LocalDateTime.now()
            )
        }

        fun of(user: UserDomain, createdAt: LocalDateTime = LocalDateTime.now()): ThreadDomain {
            return ThreadDomain(
                user = user,
                chats = emptyList(),
                createdAt = createdAt,
                lastActivityAt = createdAt
            )
        }

        fun of(
            id: UUID,
            user: UserDomain?,
            chats: List<ChatDomain>,
            createdAt: LocalDateTime,
            lastActivityAt: LocalDateTime
        ): ThreadDomain {
            return ThreadDomain(
                id = id,
                user = user,
                chats = chats,
                createdAt = createdAt,
                lastActivityAt = lastActivityAt
            )
        }


        fun fromEntity(entity: ThreadEntity?): ThreadDomain? {
            if (entity == null) return null
            return ThreadDomain(
                id = entity.id,
                user = UserDomain.fromEntity(entity.user!!),
                chats = entity.chats.map { ChatDomain.fromEntity(it) },
                createdAt = entity.createdAt,
                lastActivityAt = entity.lastActivityAt
            )
        }


    }

    fun toEntity(): ThreadEntity {
        return ThreadEntity(
            id = id,
            user = user?.toEntity(),
            lastActivityAt = lastActivityAt!!,
        )
    }
}

