package pl.tsimur.jpatest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends ResponseStatusException {

    public InvalidArgumentException(String message) {
        super(HttpStatus.BAD_REQUEST,
                "This value is not correct " + message);

    }
}
