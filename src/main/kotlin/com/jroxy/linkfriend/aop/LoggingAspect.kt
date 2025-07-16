package com.jroxy.linkfriend.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * @LogExecutionTime 어노테이션이 붙은 메서드의 실행 시간을 측정하는 AOP 클래스입니다.
 */
@Aspect
@Component
class LoggingAspect {

    private val logger: Logger = LogManager.getLogger(this::class.java)

    /**
     * @Around: 메서드 실행 전/후를 감싸는 Advice입니다.
     * - 메서드 실행 시간을 측정하고 로그로 남깁니다.
     */
    @Around("@annotation(com.jroxy.linkfriend.aop.LogExecutionTime)")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any {
        val start = System.currentTimeMillis()

        // 대상 메서드 실행
        val result = joinPoint.proceed()

        val end = System.currentTimeMillis()
        val duration = end - start

        val methodName = "${joinPoint.signature.declaringType.simpleName}.${joinPoint.signature.name}"
        logger.info("[ExecutionTime] $methodName took ${duration}ms")

        return result
    }
}