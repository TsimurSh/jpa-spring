package pl.timur.jpatest.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidArgumentException(message: String) : ResponseStatusException(
    HttpStatus.BAD_REQUEST,
    "This value is not correct $message"
)