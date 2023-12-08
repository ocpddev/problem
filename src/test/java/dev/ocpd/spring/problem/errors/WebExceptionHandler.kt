package dev.ocpd.spring.problem.errors

import dev.ocpd.spring.problem.servlet.GeneralAdviceTrait
import org.apache.catalina.connector.ClientAbortException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/*
 * This overrides the existing ProblemDetailsExceptionHandler provided by Spring Boot.
 *
 * To ensure all unknown exceptions will be handled as a ProblemDetail response, we
 * needed a catch-all exception handler that will capture all [Throwable]s. However,
 * the ProblemDetailsExceptionHandler provided by Spring Boot by default has the lowest
 * precedence, which means there's no guarantee that our catch-all exception handler will
 * be called last therefore causing undesired behaviors. The root cause of this is that
 * the [OrderComparator] will implicitly assign the lowest precedence to [Ordered] beans
 * if no explicit order is otherwise specified.
 * To solve this, we have to extend and override the default ProblemDetailsExceptionHandler
 * so that all exception handlers are within a single class and the ordering is more predictable.
 * Spring Boot will prioritize narrower exception handlers over broader ones if they are defined
 * in the same class.
 *
 * Spring Boot is also in the process of unifying their built-in exceptions, so this class
 * is more as a temporary workaround until the unification is complete.
 * See:
 * https://github.com/spring-projects/spring-boot/issues/19525
 * https://github.com/spring-projects/spring-boot/issues/33885
 */
@RestControllerAdvice
class WebExceptionHandler : ResponseEntityExceptionHandler(), GeneralAdviceTrait {

    @ExceptionHandler(ClientAbortException::class)
    fun handleClientAbortException(e: ClientAbortException) {
        // Ignore
    }
}
