package pl.timur.jpatest.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
class NotEnoughMoneyException(balance: Number) :
    ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, "💸 Not enough money. Current balance: $balance")