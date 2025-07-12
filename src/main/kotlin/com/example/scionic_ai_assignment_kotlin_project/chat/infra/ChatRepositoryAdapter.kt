package com.example.scionic_ai_assignment_kotlin_project.chat.infra

import com.example.scionic_ai_assignment_kotlin_project.auth.domain.model.UserDomain
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.model.ChatDomain
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.model.ThreadDomain
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.port.ChatRepositoryPort
import com.example.scionic_ai_assignment_kotlin_project.chat.infra.repository.ChatRepository
import com.example.scionic_ai_assignment_kotlin_project.chat.infra.repository.ThreadRepository
import java.time.LocalDateTime
import java.util.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class ChatRepositoryAdapter(
    private val chatRepository: ChatRepository,
    private val threadRepository: ThreadRepository
) : ChatRepositoryPort {

    companion object {
        private const val EXPIRATION_MINUTES = 30L;
    }

    override fun saveChat(chat: ChatDomain): ChatDomain {
        val entity = chat.toEntity()
        val savedEntity = chatRepository.save(entity)
        return ChatDomain.fromEntity(savedEntity)
    }

    override fun saveThread(thread: ThreadDomain): ThreadDomain {
        val entity = thread.toEntity()
        val savedEntity = threadRepository.save(entity)
        return ThreadDomain.fromEntity(savedEntity)!!
    }

    override fun findThreadById(threadId: UUID): ThreadDomain? {
        val entity = threadRepository.findById(threadId).orElse(null) ?: return null

        return ThreadDomain.of(
            id = entity.id!!,
            user = UserDomain.fromEntity(entity.user),
            chats = entity.chats.map { ChatDomain.fromEntity(it) },
            createdAt = entity.createdAt,
            lastActivityAt = entity.lastActivityAt
        )
    }

    override fun findActiveThreadByUserId(userId: UUID): ThreadDomain? {
        // 30분 내 사용된 스레드가 있는지 파악
        val expirationTime = LocalDateTime.now().minusMinutes(EXPIRATION_MINUTES)
        val entity =
            threadRepository.findActiveThreadByUserId(userId, expirationTime) ?: return null

        // 있으면 해당 스레드의 대화 목록을 조회
        return ThreadDomain.of(
            id = entity.id!!,
            user = UserDomain.fromEntity(entity.user),
            chats = entity.chats.map { ChatDomain.fromEntity(it) },
            createdAt = entity.createdAt,
            lastActivityAt = entity.lastActivityAt
        )
    }

    override fun findThreadsByUserId(userId: UUID, pageable: Pageable): Page<ThreadDomain> {
        return threadRepository.findByUserId(userId, pageable)
            .map { entity ->
                ThreadDomain.of(
                    id = entity.id!!,
                    user = UserDomain.fromEntity(entity.user),
                    chats = entity.chats.map { ChatDomain.fromEntity(it) },
                    createdAt = entity.createdAt,
                    lastActivityAt = entity.lastActivityAt
                )
            }
    }

    override fun findAllThreads(pageable: Pageable): Page<ThreadDomain> {
        return threadRepository.findAll(pageable)
            .map { entity ->
                ThreadDomain.of(
                    id = entity.id!!,
                    user = UserDomain.fromEntity(entity.user),
                    chats = entity.chats.map { ChatDomain.fromEntity(it) },
                    createdAt = entity.createdAt,
                    lastActivityAt = entity.lastActivityAt
                )
            }
    }

    override fun deleteThread(threadId: UUID): Boolean {
        if (!threadRepository.existsById(threadId)) {
            return false
        }
        // 채팅 먼저 삭제
        val chats = chatRepository.findByThreadId(threadId)
        chatRepository.deleteAll(chats)
        // 스레드 삭제
        threadRepository.deleteById(threadId)
        return true
    }

    override fun existsByThreadId(threadId: UUID): Boolean {
        return threadRepository.existsById(threadId)
    }
}
