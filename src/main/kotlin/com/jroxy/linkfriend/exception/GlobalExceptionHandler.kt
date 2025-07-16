package com.jroxy.linkfriend.exception

import com.jroxy.linkfriend.common.dto.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 프로젝트 전역에서 발생하는 예외를 공통으로 처리하는 핸들러 클래스
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    /**
     * 커스텀 예외(LinkFriendException)를 처리합니다.
     * 예외에 담긴 상태 코드와 메시지를 기반으로 응답을 생성합니다.
     */
    @ExceptionHandler(LinkFriendException::class)
    fun handleLinkFriendException(ex: LinkFriendException): ResponseEntity<ApiResponse<Unit>> {
        val response = ApiResponse.failure<Unit>(ex.errorCode.code, ex.message)
        return ResponseEntity.status(ex.errorCode.status).body(response)
    }

    /**
     * 처리되지 않은 모든 일반 예외를 잡아 500 에러로 응답합니다.
     * 예외 메시지는 숨기고, "서버 내부 오류"라는 메시지를 반환합니다.
     */
    @ExceptionHandler(Exception::class)
    fun handleUnhandledException(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = ErrorCode.INTERNAL_ERROR.code,
            message = "서버 내부 오류가 발생했습니다."
        )
        return ResponseEntity.status(500).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Map<String, String>>> {
        // 필드별 오류 메시지를 담을 맵 생성
        val errors = mutableMapOf<String, String>()

        ex.bindingResult.fieldErrors.forEach { error ->
            val fieldName = error.field
            val errorMessage = error.defaultMessage ?: "올바르지 않은 값입니다."
            errors[fieldName] = errorMessage
        }

        // 실패 응답 생성
        val response = ApiResponse.failure<Map<String, String>>(
            code = ErrorCode.INVALID_INPUT.code,
            message = "입력값이 유효하지 않습니다.",
        ).copy(data = errors)

        return ResponseEntity.status(ErrorCode.INVALID_INPUT.status).body(response)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotSupported(ex: HttpRequestMethodNotSupportedException): ResponseEntity<String> {
        logger.warn("❗ 잘못된 HTTP 메서드 요청: ${ex.method} -> 지원되지 않음")
        return ResponseEntity("요청 메서드가 잘못되었습니다.", HttpStatus.METHOD_NOT_ALLOWED)
    }
}