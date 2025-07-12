package com.example.scionic_ai_assignment_kotlin_project.analytics.application

import org.springframework.stereotype.Service

@Service
class AnalyticsService {


    // 사용자 활동 기록 조회(관리자만 가능)
    // 회원가입, 로그인, 대화 생성 수를 응답
    // 요청 시점으로부터 하루 동안의 기록을 응답
    fun getUserActivity(){

    }

    // 보고서 생성(관리자만 가능)
    // csv 형태의 보고서 생성
    // 보고서에는 모든 사용자의 대화 목록과 어떤 사용자가 생성했는지 포함됩니다.
    // 요청 시점으로부터 하루 동안의 보고서가 생성되어야 합니다.
    fun createReport(){

    }
}