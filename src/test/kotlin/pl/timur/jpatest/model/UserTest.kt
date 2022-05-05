package pl.timur.jpatest.model

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class UserTest {
    var mapper = ObjectMapper()

    @Test
    @Disabled
    fun createUser() {
        val user: User = User().apply {
            id = 1
            password = "SECRET"
        }


        println("User with id & password : $user")

        val json = mapper.writeValueAsString(user)
        assertEquals(
            """{"id":1,"email":null,"password":"SECRET","name":null,"surname":null,"balance":null,"age":null,"lastPayment":null}""",
            json
        )
    }
}