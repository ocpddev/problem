package dev.ocpd.spring.problem.security.servlet

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.web.servlet.HandlerExceptionResolver

class SecurityProblemSupport(private val resolver: HandlerExceptionResolver) : AuthenticationEntryPoint,
    AuthenticationFailureHandler, AccessDeniedHandler {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException,
    ) {
        resolver.resolveException(request, response, null, exception)
    }

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException,
    ) {
        resolver.resolveException(request, response, null, exception)
    }

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        resolver.resolveException(request, response, null, accessDeniedException)
    }
}
