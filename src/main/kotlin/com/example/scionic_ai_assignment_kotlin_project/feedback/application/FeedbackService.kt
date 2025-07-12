package com.example.scionic_ai_assignment_kotlin_project.feedback.application

import org.springframework.stereotype.Service

@Service
class FeedbackService {

    // 피드백 생성
    // 자신이 생성한 대화에 대해서만 생성가능, 관리자는 모든 대화에 대해 생성 가능, 각 사용자는 하나에 대화에 오직 하나의 피드백 생성(하나의 대화에는 서로 다른 사용자들이 생성한 n개의 피드백이 존재 가능)
    fun createFeedback(){

    }

    // 자신이 생성한 피드백만 조회가능. 관리자는 모든 피드백 조회가능
    // 생성일시 정렬, 페이지네이션
    // 긍정 부정 유무로 필터링
    fun finAllFeedbacks(isPositive : Boolean){

    }

    // 관리자는 피드백의 상태를 업데이트 가능(일반 member는 불가)
    fun updateStatus(){

    }
}