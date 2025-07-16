package com.jroxy.linkfriend.common.util

import org.slf4j.MDC

/**
 * 현재 요청의 MDC(Context)에서 사용자 정보를 조회할 수 있는 유틸 클래스입니다.
 * - 주로 서비스 계층에서 인증된 사용자 ID를 가져올 때 사용됩니다.
 */
object RequestContext {

    /**
     * AuthenticationInterceptor에서 저장한 userId를 MDC에서 꺼냅니다.
     */
    fun getUserId(): String? = MDC.get("userId")
}