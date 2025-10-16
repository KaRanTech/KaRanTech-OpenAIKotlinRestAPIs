package com.karantech.karantech.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig {
    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val config = CorsConfiguration()
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return CorsWebFilter(source)
    }

    @Bean
    fun customOpenAPI(): OpenAPI = OpenAPI().info(
        Info()
            .title("Karantech AI REST APIs")
            .version("1.0.0")
            .description("Reactive Kotlin APIs integrated with OpenAI & Image Gen")
    )
}
