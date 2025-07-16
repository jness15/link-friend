package com.jroxy.linkfriend.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

/**
 * 요청 정보를 로깅하고, 고유 Request ID를 MDC에 저장하는 인터셉터입니다.
 * - 모든 요청에 대해 URI, HTTP Method, 클라이언트 IP를 로그로 출력합니다.
 * - X-Request-ID 헤더가 있으면 사용하고, 없으면 UUID를 생성합니다.
 * - MDC (Mapped Diagnostic Context)를 활용해 로그에 requestId를 삽입할 수 있습니다.
 */
@Component
class RequestLoggingInterceptor : HandlerInterceptor {

    private val logger: Logger = LogManager.getLogger(this::class.java)

    /**
     * 컨트롤러 진입 전에 호출됩니다.
     * - 요청 정보를 기반으로 로그를 출력합니다.
     * - requestId를 MDC에 저장합니다.
     */
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        // 요청 헤더에서 X-Request-ID 가져오기 (없으면 UUID 생성)
        val requestId = request.getHeader("X-Request-ID") ?: UUID.randomUUID().toString()

        // Log4j2에서 사용할 수 있도록 MDC에 requestId 저장 (%X{requestId}로 출력 가능)
        MDC.put("requestId", requestId)

        val method = request.method
        val uri = request.requestURI
        val ip = request.remoteAddr

        logger.info("[REQUEST] $method $uri [RequestID: $requestId] [IP: $ip]")

        return true
    }

    /**
     * 요청 처리 완료 후 호출됩니다.
     * - MDC에 저장된 값을 정리하여 메모리 누수를 방지합니다.
     */
    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        MDC.clear() // 요청 처리 후 MDC cleanup
    }
}