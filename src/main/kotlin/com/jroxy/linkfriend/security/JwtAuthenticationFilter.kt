package com.jroxy.linkfriend.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    /**
     * 요청마다 실행되는 필터
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Authorization 헤더에서 Bearer 토큰 추출
        val token = resolveToken(request)

        // 토큰이 존재하고 유효한 경우 인증 처리
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val userId = jwtTokenProvider.getUserIdFromToken(token)

            // 인증 객체 생성 (사용자 역할 정보는 생략하거나 추후 추가 가능)
            val authentication = UsernamePasswordAuthenticationToken(userId, null, emptyList())
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

            // SecurityContext에 인증 정보 등록
            SecurityContextHolder.getContext().authentication = authentication
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response)
    }

    /**
     * Authorization 헤더에서 Bearer 토큰 추출
     */
    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}