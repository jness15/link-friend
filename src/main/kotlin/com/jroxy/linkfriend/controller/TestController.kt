package com.jroxy.linkfriend.controller

import com.jroxy.linkfriend.aop.LogExecutionTime
import com.jroxy.linkfriend.common.dto.ApiResponse
import com.jroxy.linkfriend.common.util.RequestContext
import com.jroxy.linkfriend.common.util.ResponseUtil
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/test")
@Validated
class TestController {

    private val logger = LoggerFactory.getLogger(TestController::class.java)

    @GetMapping("/public")
    fun publicEndpoint(): ResponseEntity<ApiResponse<String>> {
        logger.info("✅ /api/test/public 호출됨")
        return ResponseUtil.ok<String>("공개 API 응답")
    }

    @GetMapping("/protected")
    fun protectedEndpoint(): ResponseEntity<ApiResponse<String>> {
        val userId = RequestContext.getUserId() ?: "anonymous"
        return ResponseUtil.ok<String>("인증된 사용자: $userId")
    }

    data class TestRequest(
        @field:Email(message = "이메일 형식이 올바르지 않습니다.")
        val email: String,

        @field:Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
        val password: String
    )

    @PostMapping("/validate")
    fun validateTest(@RequestBody @Validated request: TestRequest): ResponseEntity<ApiResponse<Unit>> {
        return ResponseUtil.ok("입력값 검증 통과")
    }

    @LogExecutionTime
    @GetMapping("/slow")
    fun slowApi(): ResponseEntity<ApiResponse<String>> {
        Thread.sleep(500)
        return ResponseUtil.ok<String>("응답 지연 테스트 완료")
       }
}