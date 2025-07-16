package com.jroxy.linkfriend.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}")
    secret: String,

    @Value("\${jwt.expiration}")
    private val validityInMs: Long
) {
    // 시크릿 키 생성
    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    /**
     * 사용자 ID를 바탕으로 JWT 토큰을 생성
     */
    fun createToken(userId: String): String {
        val now = Date()
        val expiryDate = Date(now.time + validityInMs)

        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * JWT 토큰에서 사용자 ID 추출
     */
    fun getUserIdFromToken(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    /**
     * 토큰 유효성 검사
     */
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            true
        } catch (ex: JwtException) {
            false
        }
    }
}