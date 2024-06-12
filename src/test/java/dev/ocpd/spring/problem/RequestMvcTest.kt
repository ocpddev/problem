package dev.ocpd.spring.problem

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@AutoConfigureMockMvc
@WebMvcTest(RequestTestController::class)
class RequestMvcTest(@Autowired private val mockMvc: MockMvc) {

    @Test
    fun `should return 400 and custom error message when request body is invalid`() {
        val request = """
            {
                "name": " "
            }
        """.trimIndent()
        mockMvc.post("/api/test/request") {
            contentType = MediaType.APPLICATION_JSON
            content = request
        }
                .andExpect {
                    status { isBadRequest() }
                    jsonPath("$.detail") { value("name must be non-blank") }
                }
    }
}

@RestController
class RequestTestController {

    @PostMapping("/api/test/request")
    fun withBodyRequest(@RequestBody request: RequestTest): String {
        return request.name
    }
}

data class RequestTest(val name: String) {
    init {
        require(name.isNotBlank()) { "name must be non-blank" }
    }
}
