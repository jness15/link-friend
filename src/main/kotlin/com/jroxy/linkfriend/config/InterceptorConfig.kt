package com.jroxy.linkfriend.config

import com.jroxy.linkfriend.interceptor.AuthenticationInterceptor
import com.jroxy.linkfriend.interceptor.RequestLoggingInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * 인터셉터 등록 설정 클래스
 * - 인증 및 요청 로깅 인터셉터를 모두 등록합니다.
 */
@Configuration
class InterceptorConfig(
    private val requestLoggingInterceptor: RequestLoggingInterceptor,
    private val authenticationInterceptor: AuthenticationInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(requestLoggingInterceptor)
            .addPathPatterns("/**") // 전체 요청에 로깅

        registry.addInterceptor(authenticationInterceptor)
            .addPathPatterns("/api/**")              // 인증이 필요한 API만 지정
            .excludePathPatterns("/api/auth/**")     // 로그인/회원가입 제외
    }
}