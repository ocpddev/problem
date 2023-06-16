package dev.ocpd.spring.problem

class BadCredentialProblem : ForbiddenProblem(
    title = "Bad Credential",
    detail = "The provided credential does not match."
)

class IllegalAccessProblem(detail: String) : ForbiddenProblem(
    title = "Illegal Access",
    detail = detail
)

class LoginAlreadyUsedProblem : BadRequestProblem(
    detail = "Login name already used!"
)

class EmailAlreadyUsedProblem : BadRequestProblem(
    detail = "Email already used!"
)

class InvalidPrincipalProblem : ForbiddenProblem(
    title = "Invalid Principal",
    detail = "The request cannot proceed with the current user."
)
