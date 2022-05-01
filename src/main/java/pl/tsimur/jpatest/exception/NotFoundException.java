package pl.tsimur.jpatest.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class NotFoundException extends ResponseStatusException {
    public NotFoundException(String message) {
        super(NOT_FOUND, message);
    }
}
