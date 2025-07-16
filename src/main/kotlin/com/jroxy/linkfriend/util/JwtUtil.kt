package com.jroxy.linkfriend.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.*
import javax.crypto.SecretKey

/**
 * JWT 토큰 검증 및 파싱을 위한 유틸 클래스
 */
object JwtUtil {
    // 임시로 HS256 비밀키 지정 (운영에선 환경변수로 관리해야 안전)
    private const val SECRET = "linkfriend-super-secret-key-for-jwt-signing"
    private val key: SecretKey = Keys.hmacShaKeyFor(SECRET.toByteArray())

    /**
     * JWT에서 Claims(정보) 추출
     */
    fun parseToken(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    /**
     * 토큰에서 사용자 ID 추출 (claim에 "userId"가 있어야 함)
     */
    fun extractUserId(token: String): String {
        val claims = parseToken(token)
        return claims["userId"] as? String ?: throw IllegalArgumentException("userId claim 누락")
    }
}