package pl.timur.jpatest.model.dto

import javax.validation.constraints.Email

class LoginDto(
    @Email
    val email: String,
    val password: String
)
