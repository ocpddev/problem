package dev.ocpd.spring.problem.reactive

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebExchange

interface GeneralAdviceTrait : AdviceTrait {

    @ExceptionHandler(Throwable::class)
    fun handleInternalServerError(e: Throwable, exchange: ServerWebExchange) =
        create(exchange, HttpStatus.INTERNAL_SERVER_ERROR, e)
}
