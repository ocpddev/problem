package dev.ocpd.spring.problem.servlet

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.NativeWebRequest

interface GeneralAdviceTrait : AdviceTrait {

    @ExceptionHandler(Throwable::class)
    fun handleInternalServerError(e: Throwable, request: NativeWebRequest) =
        create(request, HttpStatus.INTERNAL_SERVER_ERROR, e)
}
