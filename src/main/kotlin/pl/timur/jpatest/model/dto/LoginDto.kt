package pl.timur.jpatest.model.dto

import pl.timur.jpatest.model.User
import javax.validation.constraints.Email

data class LoginDto(
    @Email
    val email: String,
    val password: String
) {
    fun toUser() = User(email = email, password = password)
}
