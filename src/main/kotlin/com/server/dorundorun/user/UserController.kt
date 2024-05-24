package com.server.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class UserController {

    @GetMapping("/public")
    fun publicEndpoint(): Mono<String> {
        return Mono.just("This is a public endpoint")
    }

    @GetMapping("/private")
    fun privateEndpoint(): Mono<String> {
        return Mono.just("This is a private endpoint")
    }
}
