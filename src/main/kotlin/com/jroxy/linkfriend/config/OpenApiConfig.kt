package com.jroxy.linkfriend.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme as SwaggerSecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Swagger(OpenAPI) 기본 설정 클래스
 * - API 문서 메타데이터 및 JWT 인증 스키마 설정 포함
 */
@Configuration
@SecurityScheme(
    name = "JWT",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
class OpenApiConfig {

    /**
     * OpenAPI 문서 정보 및 보안 설정 등록
     */
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("LinkFriend API")
                    .description("링크친구 백엔드 API 명세서")
                    .version("v1.0.0")
            )
            .components(
                Components().addSecuritySchemes(
                    "JWT", SwaggerSecurityScheme()
                        .type(SwaggerSecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            )
    }
}