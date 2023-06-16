package dev.ocpd.spring.problem.security.servlet

import dev.ocpd.spring.problem.servlet.AdviceTrait
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.NativeWebRequest

interface SecurityAdviceTrait : AdviceTrait {

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthentication(e: AuthenticationException, request: NativeWebRequest) =
        create(request, HttpStatus.UNAUTHORIZED, e)

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(e: AccessDeniedException, request: NativeWebRequest) =
        create(request, HttpStatus.FORBIDDEN, e)
}
