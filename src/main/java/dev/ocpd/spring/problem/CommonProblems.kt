package dev.ocpd.spring.problem

class ResourceNotFoundProblem : NotFoundProblem(
    detail = "Specified resource could not be found"
)

/**
 * Indicates the request it self is not structurally valid
 * and, most likely, it is mistakenly crafted by frontend,
 * which is NOT the end-user's fault.
 */
class InvalidRequestProblem(detail: String?) : BadRequestProblem(
    title = "Invalid Request",
    detail = detail
)

/**
 * Indicates the request it self is structurally valid, but
 * the actual requested input can not be processed.
 *
 * The different between this and [InvalidRequestProblem] is that
 * the requested input may be controlled solely by the end-user,
 * which in turn is the end-user's fault.
 */
class BadUserRequestProblem(detail: String?) : BadRequestProblem(
    detail = detail
)
