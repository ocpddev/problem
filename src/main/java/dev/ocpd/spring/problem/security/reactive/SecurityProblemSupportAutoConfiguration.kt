package dev.ocpd.spring.problem.security.reactive

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.REACTIVE
import org.springframework.context.annotation.Bean
import org.springframework.security.web.server.SecurityWebFilterChain

@AutoConfiguration
@ConditionalOnWebApplication(type = REACTIVE)
@ConditionalOnClass(SecurityWebFilterChain::class, ObjectMapper::class)
@ConditionalOnMissingBean(SecurityProblemSupport::class)
class SecurityProblemSupportAutoConfiguration(
    private val advice: SecurityAdviceTrait,
    private val mapper: ObjectMapper
) {
    @Bean
    fun securityProblemSupport() = SecurityProblemSupport(advice, mapper)
}
