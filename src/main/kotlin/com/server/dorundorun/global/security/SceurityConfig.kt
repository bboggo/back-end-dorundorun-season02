package backend.taskweaver.global.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint

@Configuration
@EnableMethodSecurity
class SecurityConfig {

    private val allowedUrls = arrayOf("/")

    @Bean
    @Throws(Exception::class)
    fun HttpSecurity.filterChain(): SecurityFilterChain {
        return csrf { it.disable() }
                .httpBasic { it.disable() }
                .formLogin { it.disable() }
                .authorizeHttpRequests { requests ->
                    requests
                            .requestMatchers(*allowedUrls).permitAll()
                            .anyRequest().authenticated()
                }
                .sessionManagement { sessionManagement ->
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .exceptionHandling { exceptionHandling ->
                    exceptionHandling.authenticationEntryPoint(Http403ForbiddenEntryPoint())
                }

                .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
