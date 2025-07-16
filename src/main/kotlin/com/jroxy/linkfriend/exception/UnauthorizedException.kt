package com.jroxy.linkfriend.exception

/**
 * JWT 인증 실패 시 발생하는 401 예외
 */
class UnauthorizedException : LinkFriendException(
    message = "인증이 필요한 요청입니다.",
    errorCode = ErrorCode.UNAUTHORIZED
)