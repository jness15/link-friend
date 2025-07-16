package com.jroxy.linkfriend.aop

/**
 * 메서드 실행 시간을 측정하고 로그로 출력하고 싶을 때 사용하는 커스텀 어노테이션입니다.
 * 예: @LogExecutionTime
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LogExecutionTime