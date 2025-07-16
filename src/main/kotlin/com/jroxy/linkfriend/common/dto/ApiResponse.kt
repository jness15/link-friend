package com.jroxy.linkfriend.common.dto

/**
 * 모든 API 응답을 통일된 형태로 감싸주는 래퍼 클래스입니다.
 * - code: 결과 상태 코드 (예: SUCCESS, INVALID_INPUT)
 * - message: 응답에 대한 설명 메시지
 * - data: 실제 비즈니스 응답 데이터 (제네릭)
 */
data class ApiResponse<T>(
    val code: String = "SUCCESS",
    val message: String = "요청이 성공적으로 처리되었습니다.",
    val data: T? = null
) {
    companion object {
        /**
         * 성공 응답 생성 유틸 메서드
         */
        fun <T> success(data: T? = null, message: String = "요청이 성공적으로 처리되었습니다."): ApiResponse<T> {
            return ApiResponse(code = "SUCCESS", message = message, data = data)
        }

        /**
         * 실패 응답 생성 유틸 메서드
         */
        fun <T> failure(code: String, message: String): ApiResponse<T> {
            return ApiResponse(code = code, message = message, data = null)
        }
    }
}