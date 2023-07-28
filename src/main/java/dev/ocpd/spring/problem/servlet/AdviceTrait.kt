package dev.ocpd.spring.problem.servlet

import dev.ocpd.slf4k.slf4j
import dev.ocpd.slf4k.warn
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.Series.CLIENT_ERROR
import org.springframework.http.HttpStatus.Series.SERVER_ERROR
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.NativeWebRequest

private val log = slf4j<AdviceTrait>()

interface AdviceTrait {

    fun log(
        request: NativeWebRequest,
        problem: ProblemDetail,
        status: HttpStatus,
        e: Throwable
    ) {
        when (status.series()) {
            CLIENT_ERROR -> log.warn { "${status.reasonPhrase}: ${e.message}" }
            SERVER_ERROR -> log.error(status.reasonPhrase, e)
            else -> {}
        }
    }

    fun create(
        request: NativeWebRequest,
        status: HttpStatus,
        e: Throwable
    ): ResponseEntity<ProblemDetail> {
        /*
         * The detail parameter for ProblemDetail.forStatusAndDetail is mis-annotated
         * as non-null, but it is actually nullable. Below uses a workaround to construct
         * ProblemDetail with nullable messages.
         */
        val problem = ProblemDetail.forStatus(status).apply { detail = e.message }
        log(request, problem, status, e)
        return ResponseEntity(problem, status)
    }
}
