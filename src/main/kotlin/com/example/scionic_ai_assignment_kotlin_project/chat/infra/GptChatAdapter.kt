package com.example.scionic_ai_assignment_kotlin_project.chat.infra

import com.example.scionic_ai_assignment_kotlin_project.chat.domain.port.GptChatPort
import com.example.scionic_ai_assignment_kotlin_project.chat.domain.vo.ChatMessage
import org.springframework.stereotype.Component

@Component
class GptChatAdapter : GptChatPort {
    override fun complete(messages: List<ChatMessage>, model: String): String {
        // TODO: 실제 OpenAI API 호출 구현
        // 현재는 임시 응답을 반환
        val lastMessage = messages.lastOrNull()
        return if (lastMessage != null) {
            "안녕하세요! ${lastMessage.content}에 대한 답변입니다. (모델: $model)"
        } else {
            "안녕하세요! 무엇을 도와드릴까요?"
        }
    }

    override fun completeStream(messages: List<ChatMessage>, model: String): String {
        // TODO: 실제 OpenAI API 스트리밍 호출 구현
        // 현재는 일반 응답과 동일하게 처리
        return complete(messages, model)
    }
}
