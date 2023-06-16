package dev.ocpd.spring.problem.security.servlet

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET
import org.springframework.context.annotation.Bean
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.HandlerExceptionResolver

@AutoConfiguration
@ConditionalOnWebApplication(type = SERVLET)
@ConditionalOnClass(SecurityFilterChain::class)
@ConditionalOnMissingBean(SecurityProblemSupport::class)
class SecurityProblemSupportAutoConfiguration(
    @Qualifier(DispatcherServlet.HANDLER_EXCEPTION_RESOLVER_BEAN_NAME)
    private val resolver: HandlerExceptionResolver,
) {
    @Bean
    fun securityProblemSupport() = SecurityProblemSupport(resolver)
}
