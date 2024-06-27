package pl.timur.jpatest.controller

import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.timur.jpatest.model.Tariff
import pl.timur.jpatest.model.User
import pl.timur.jpatest.model.dto.UserMiniDto
import pl.timur.jpatest.service.UserService

@Validated
@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @GetMapping("/{id}")
    @Operation(summary = "👨🏻 Find user by id")
    fun findUser(@PathVariable id: @Positive Int): User {
        return userService.findOrThrow(id)
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "🔫 register user")
    fun registerUser(@RequestBody user: @Valid User): User {
        val userSaved: User = userService.save(user)
        log.info("Saved user {}", userSaved)
        return userSaved
    }

    @GetMapping
    fun findAllUsers(): List<UserMiniDto> {
        val found: List<UserMiniDto> = userService.findAllMiniUsers()
        log.info("Users with qualification are found {}", found.size)
        return found
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: @NotBlank String): List<User> {
        return userService.findAll(name)
    }

    @PutMapping("/{id}/{subs}")
    @Operation(summary = "⚓️ Buy subscription")
    fun bySubscription(@PathVariable id: Int, @PathVariable("subs") tariff: String) {
        userService.buySubscription(id, Tariff.valueOf(tariff))
    }

    @GetMapping("/employer/{name}")
    @Operation(summary = "Find pensioners employees in organization")
    fun findPensionersInOrganization(@PathVariable name: String): Int {
        return userService.findCountEmployeesWithPensionerAge(name)
    }

    @GetMapping("/younger/{age}")
    @Operation(summary = "Find all young users", description = "You can find all users younger then submitted age")
    fun findYoungerUsers(@PathVariable age: Int): List<User> {
        return userService.findAllYounger(age)
    }
}
