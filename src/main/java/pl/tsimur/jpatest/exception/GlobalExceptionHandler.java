package pl.tsimur.jpatest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public String unknownExceptionHandler(Exception ex) {
        log.error("🐥 UNKNOWN: " + ex.getMessage());
        return ex.getMessage();
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(value = {EntityNotFoundException.class, NotFoundException.class})
    public String notFoundExceptionHandler(Exception ex) {
        log.error("⛑ NOT FOUND: " + ex.getMessage());
        return ex.getMessage();
    }

    @ResponseStatus(PAYMENT_REQUIRED)
    @ExceptionHandler(value = {NotEnoughMoneyException.class})
    public String notEnoughMoneyExceptionHandler(Exception ex) {
        log.error("💰 NOT ENOUGH: " + ex.getMessage());
        return ex.getMessage();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {InvalidArgumentException.class})
    public String invalidArgumentExceptionHandler(Exception ex) {
        log.error("🤨 INVALID: " + ex.getMessage());
        return ex.getMessage();
    }
}
