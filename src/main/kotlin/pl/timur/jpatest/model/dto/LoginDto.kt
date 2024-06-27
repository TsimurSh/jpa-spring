package pl.timur.jpatest.model.dto

import jakarta.validation.constraints.Email
import pl.timur.jpatest.model.User

data class LoginDto(
    @Email
    val email: String,
    val password: String
) {
    fun toUser() = User(email = email, password = password)
}
