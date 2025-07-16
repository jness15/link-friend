package com.jroxy.linkfriend.exception

/**
 * 클라이언트에게 반환할 오류 응답 데이터 클래스
 * code: 오류 코드 문자열 (예: "NOT_FOUND")
 * message: 상세 오류 메시지
 */
data class ErrorResponse(
    val code: String,
    val message: String
)