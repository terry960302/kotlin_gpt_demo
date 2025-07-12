package com.example.scionic_ai_assignment_kotlin_project.chat.domain.model

import com.example.scionic_ai_assignment_kotlin_project.auth.domain.model.UserDomain
import com.example.scionic_ai_assignment_kotlin_project.chat.infra.entity.ChatEntity
import java.time.LocalDateTime
import java.util.UUID

data class ChatDomain
private constructor(
    val id: UUID? = null,
    val user: UserDomain?,
    val thread: ThreadDomain?,
    val question: String,
    val answer: String,
    val createdAt: LocalDateTime,
) {
    init {
        require(question.isNotBlank()) { "질문은 필수입니다." }
        require(question.length <= 1000) { "질문은 1000자를 초과할 수 없습니다." }
        require(answer.isNotBlank()) { "답변은 필수입니다." }
        require(!createdAt.isAfter(LocalDateTime.now())) { "생성일시는 미래일 수 없습니다." }
    }

    companion object {
        fun of(
            user: UserDomain?,
            thread: ThreadDomain?,
            question: String,
            answer: String,
            createdAt: LocalDateTime = LocalDateTime.now()
        ): ChatDomain {
            return ChatDomain(
                user = user,
                thread = thread,
                question = question,
                answer = answer,
                createdAt = createdAt
            )
        }

        fun of(
            id: UUID,
            user: UserDomain?,
            thread: ThreadDomain?,
            question: String,
            answer: String,
            createdAt: LocalDateTime
        ): ChatDomain {
            return ChatDomain(
                id = id,
                user = user,
                thread = thread,
                question = question,
                answer = answer,
                createdAt = createdAt
            )
        }

        fun fromEntity(entity: ChatEntity): ChatDomain {
            return ChatDomain(
                id = entity.id,
                user = UserDomain.fromEntity(entity.user),
                thread = ThreadDomain.fromEntity(entity.thread),
                question = entity.question,
                answer = entity.answer,
                createdAt = entity.createdAt
            )
        }


    }

    fun toEntity(): ChatEntity {
        return ChatEntity(
            id = id,
            user = user?.toEntity(),
            thread = thread?.toEntity(),
            question = question,
            answer = answer,
            createdAt = createdAt
        )
    }
}
