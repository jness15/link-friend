package com.jroxy.linkfriend.common.util

import com.jroxy.linkfriend.common.dto.ApiResponse
import org.springframework.http.ResponseEntity

/**
 * 컨트롤러 응답을 간결하게 생성할 수 있도록 도와주는 헬퍼 함수 모음입니다.
 */
object ResponseUtil {

    /**
     * 성공 응답 반환 (200 OK)
     */
    fun <T> ok(data: T, message: String = "요청이 성공적으로 처리되었습니다."): ResponseEntity<ApiResponse<T>> {
        return ResponseEntity.ok(ApiResponse.success(data, message))
    }

    /**
     * 단순 메시지만 포함된 성공 응답
     */
    fun ok(message: String = "요청이 성공적으로 처리되었습니다."): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity.ok(ApiResponse.success<Unit>(null, message))
    }
}