package com.server.dorundorun.global.security.config

import com.server.dorundorun.global.config.CorsConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    private val corsConfig: CorsConfig? = null

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfig?.corsConfigurationSource() ?: null) }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .headers { it -> it.frameOptions { it.mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN) } }
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .authorizeExchange {
                it.pathMatchers("/").permitAll()
                it.anyExchange().authenticated()
            }
            .oauth2Login{ }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}