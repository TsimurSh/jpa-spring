package pl.tsimur.jpatest.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.PAYMENT_REQUIRED;

@ResponseStatus(PAYMENT_REQUIRED)
public class NotEnoughMoneyException extends ResponseStatusException {
    public NotEnoughMoneyException(Number balance) {
        super(PAYMENT_REQUIRED, "💸 Not enough money. Current balance: " + balance);
    }
}
