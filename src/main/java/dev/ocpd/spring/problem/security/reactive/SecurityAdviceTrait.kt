package dev.ocpd.spring.problem.security.reactive

import dev.ocpd.spring.problem.reactive.AdviceTrait
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebExchange

interface SecurityAdviceTrait : AdviceTrait {

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthentication(e: AuthenticationException, exchange: ServerWebExchange) =
        create(exchange, HttpStatus.UNAUTHORIZED, e)

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(e: AccessDeniedException, exchange: ServerWebExchange) =
        create(exchange, HttpStatus.UNAUTHORIZED, e)
}
