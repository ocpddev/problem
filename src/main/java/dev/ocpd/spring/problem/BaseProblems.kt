package dev.ocpd.spring.problem

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponseException
import java.net.URI

abstract class AbstractThrowableProblem(
    type: URI? = null,
    title: String? = null,
    status: HttpStatus,
    detail: String? = null,
    cause: Throwable? = null
) : ErrorResponseException(status, ProblemDetail.forStatus(status), cause) {
    init {
        type?.let { setType(it) }
        title?.let { setTitle(it) }
        detail?.let { setDetail(it) }
    }
}

open class BadRequestProblem(
    title: String? = HttpStatus.BAD_REQUEST.reasonPhrase,
    detail: String? = null,
    type: URI? = null
) : AbstractThrowableProblem(type, title, HttpStatus.BAD_REQUEST, detail)

open class UnauthorizedProblem(
    title: String? = HttpStatus.UNAUTHORIZED.reasonPhrase,
    detail: String? = "Full authentication is required to access this resource",
    type: URI? = null
) : AbstractThrowableProblem(type, title, HttpStatus.UNAUTHORIZED, detail)

open class ForbiddenProblem(
    title: String? = HttpStatus.FORBIDDEN.reasonPhrase,
    detail: String? = "Access is denied",
    type: URI? = null
) : AbstractThrowableProblem(type, title, HttpStatus.FORBIDDEN, detail)

open class NotFoundProblem(
    title: String? = HttpStatus.NOT_FOUND.reasonPhrase,
    detail: String? = null,
    type: URI? = null
) : AbstractThrowableProblem(type, title, HttpStatus.NOT_FOUND, detail)
