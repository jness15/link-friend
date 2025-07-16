package com.jroxy.linkfriend.exception

open class LinkFriendException(
    override val message: String,
    val errorCode: ErrorCode
) : RuntimeException(message)

enum class ErrorCode(val status: Int, val code: String) {
    INVALID_INPUT(400, "INVALID_INPUT"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_ERROR(500, "INTERNAL_ERROR")
}