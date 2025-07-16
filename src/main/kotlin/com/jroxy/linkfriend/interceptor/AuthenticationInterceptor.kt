package com.jroxy.linkfriend.interceptor

import com.jroxy.linkfriend.exception.UnauthorizedException
import com.jroxy.linkfriend.util.JwtUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

/**
 * 요청 헤더에 포함된 JWT 토큰을 검증하는 인터셉터입니다.
 * - "Authorization: Bearer <token>" 형식을 파싱하고 검증합니다.
 * - 유효한 토큰인 경우, 사용자 ID를 MDC에 저장합니다.
 */
@Component
class AuthenticationInterceptor : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val authHeader = request.getHeader("Authorization")
            ?: throw UnauthorizedException()

        if (!authHeader.startsWith("Bearer ")) {
            throw UnauthorizedException()
        }

        val token = authHeader.removePrefix("Bearer ").trim()

        try {
            // 토큰 검증 및 사용자 ID 추출
            val userId = JwtUtil.extractUserId(token)

            // 요청 처리 흐름(MDC)에 사용자 ID 저장
            MDC.put("userId", userId)

        } catch (e: Exception) {
            throw UnauthorizedException()
        }

        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        MDC.remove("userId") // 요청 완료 후 사용자 ID 제거
    }
}