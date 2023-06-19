package dev.ocpd.spring.problem.security.reactive

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * Inspired by Zalando Spring WebFlux Problem Support
 */
class SecurityProblemSupport(
    private val advice: SecurityAdviceTrait,
    private val mapper: ObjectMapper
) : ServerAuthenticationEntryPoint, ServerAccessDeniedHandler {

    override fun commence(exchange: ServerWebExchange, e: AuthenticationException): Mono<Void> {
        return advice.handleAuthentication(e, exchange).flatMap { entity -> exchange.respond(entity) }
    }

    override fun handle(exchange: ServerWebExchange, e: AccessDeniedException): Mono<Void> {
        return advice.handleAccessDenied(e, exchange).flatMap { entity -> exchange.respond(entity) }
    }

    private fun ServerWebExchange.respond(entity: ResponseEntity<ProblemDetail>): Mono<Void> {
        response.statusCode = entity.statusCode
        return try {
            val buffer = response.bufferFactory().wrap(mapper.writeValueAsBytes(entity.body))
            response.writeWith(Mono.just(buffer)).doOnError { _ ->
                DataBufferUtils.release(buffer)
            }
        } catch (ex: JsonProcessingException) {
            Mono.error(ex)
        }
    }
}
