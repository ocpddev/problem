package dev.ocpd.spring.problem.reactive

import dev.ocpd.slf4k.slf4j
import dev.ocpd.slf4k.warn
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.Series.CLIENT_ERROR
import org.springframework.http.HttpStatus.Series.SERVER_ERROR
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

private val log = slf4j<AdviceTrait>()

interface AdviceTrait {
    fun log(
        exchange: ServerWebExchange,
        problem: ProblemDetail,
        status: HttpStatus,
        e: Throwable
    ): Mono<Void> = Mono.fromRunnable {
        when (status.series()) {
            CLIENT_ERROR -> log.warn { "${status.reasonPhrase}: ${e.message}" }
            SERVER_ERROR -> log.error(status.reasonPhrase, e)
            else -> {}
        }
    }

    fun create(
        exchange: ServerWebExchange,
        status: HttpStatus,
        e: Throwable
    ): Mono<ResponseEntity<ProblemDetail>> {
        /*
         * The detail parameter for ProblemDetail.forStatusAndDetail is mis-annotated
         * as non-null, but it is actually nullable. Below uses a workaround to construct
         * ProblemDetail with nullable messages.
         */
        val problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN).apply { detail = e.message }
        val entity = ResponseEntity(problem, status)
        return log(exchange, problem, status, e).then(Mono.just(entity))
    }
}
