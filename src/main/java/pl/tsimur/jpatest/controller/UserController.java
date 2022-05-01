package pl.tsimur.jpatest.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.tsimur.jpatest.model.Tariff;
import pl.tsimur.jpatest.model.User;
import pl.tsimur.jpatest.model.dto.UserMiniDto;
import pl.tsimur.jpatest.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user") //
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "👨🏻 Find user by id")
    public User findUser(@PathVariable @Positive Integer id) {
        return userService.findOrThrow(id);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "🔫 register user")
    public User registerUser(@Valid @RequestBody User user) {
        User userSaved = userService.save(user);
        log.info("Saved user {}", userSaved);
        return userSaved;
    }

    @GetMapping
    public List<UserMiniDto> findAllUsers() {
        List<UserMiniDto> found = userService.findAllMiniUsers();
        log.info("Users with qualification are found {}", found.size());
        return found;
    }

    @GetMapping("/name/{name}")
    public List<User> findByName(@PathVariable @NotBlank String name) {
        return userService.findAll(name);
    }

    @PutMapping("/{id}/{subs}")
    @Operation(summary = "⚓️ Buy subscription")
    public void bySubscription(@PathVariable Integer id, @PathVariable("subs") String tariff) {
        userService.buySubscription(id, Tariff.valueOf(tariff));
    }

    @GetMapping("/employer/{name}")
    @Operation(summary = "Find pensioners employees in organization")
    public Integer findPensionersInOrganization(@PathVariable String name) {
        return userService.findCountEmployeesWithPensionerAge(name);
    }

    @GetMapping("/younger/{age}")
    @Operation(summary = "Find all young users",
            description = "You can find all users younger then submitted age")
    public List<User> findYoungerUsers(@PathVariable Integer age) {
        return userService.findAllYounger(age);
    }
}
