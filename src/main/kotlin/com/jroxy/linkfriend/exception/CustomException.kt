package com.jroxy.linkfriend.exception

import org.springframework.http.HttpStatus

/**
 * CustomException
 *
 * 서비스 전반에서 사용할 수 있는 공통 커스텀 예외 클래스입니다.
 * 메시지와 함께 HTTP 상태 코드(HttpStatus)를 설정할 수 있습니다.
 */
class CustomException(

    // 예외 메시지를 부모 클래스인 RuntimeException 전달
    override val message: String,

    // 기본값은 400 Bad Request, 예외 생성 시 다른 상태 코드도 지정 가능
    val status: HttpStatus = HttpStatus.BAD_REQUEST

) : RuntimeException(message) // Kotlin 클래스 상속은 : 로 표현