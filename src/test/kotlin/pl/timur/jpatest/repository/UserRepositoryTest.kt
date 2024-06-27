package pl.timur.jpatest.repository

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.timur.jpatest.model.User

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest
class UserRepositoryTest(@Autowired private val userRepository: UserRepository) {
    private val user1 = User(
        name = "Tarantul", surname = "Ivanov",
        age = 34, email = "tarantul-666@gmail.com",
        password = "ouhg938yg93ojkgrh3984ygu20jvn39420uefpj"
    )

    @Test
    @Order(2)
    fun createUserTest() {
        Assertions.assertNull(id)
        val userSaved = userRepository.save(user1)
        println("Created userSaved: $userSaved")
        println("User id = " + id)
        id = userSaved.id
        Assertions.assertNotNull(id)
        userRepository.deleteById(id)
    }

    @Test
    @Order(3)
    fun findUserByAgeAfterTest() {
        val users = userRepository.findAllByAgeAfter(18)
        Assertions.assertTrue(users.size > 4)
    }

    @Test
    @Order(4)
    fun findUserByAgeBeforeTest() {
        val users = userRepository.findAllByAgeBefore(50)
        Assertions.assertFalse(users.isEmpty())
    }

    @Test
    @Order(5)
    fun findUserByNameStartingWithTest() {
        val users = userRepository.findUserByNameStartingWith("T")
        Assertions.assertEquals("Tsima", users[0].name)
    }

    @get:Order(6)
    @get:Test
    val userByIdTest: Unit
        get() {
            val user = userRepository.findById(-2)
            Assertions.assertTrue(user.isPresent)
        }

    @get:Order(7)
    @get:Test
    val userById___WhenNotExistsTest: Unit
        get() {
            val userOpt = userRepository.findById(666)
            Assertions.assertFalse(userOpt.isPresent)
        }

    companion object {
        private var id: Int? = null
    }
}