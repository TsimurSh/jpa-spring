package pl.timur.jpatest.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityNotFoundException

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @ExceptionHandler(value = [Exception::class])
    fun unknownExceptionHandler(ex: Exception): String? {
        log.error("🐥 UNKNOWN: " + ex.message)
        return ex.message
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [EntityNotFoundException::class, NotFoundException::class])
    fun notFoundExceptionHandler(ex: Exception): String? {
        log.error("⛑ NOT FOUND: " + ex.message)
        return ex.message
    }

    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    @ExceptionHandler(value = [NotEnoughMoneyException::class])
    fun notEnoughMoneyExceptionHandler(ex: Exception): String? {
        log.error("💰 NOT ENOUGH: " + ex.message)
        return ex.message
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [InvalidArgumentException::class])
    fun invalidArgumentExceptionHandler(ex: Exception): String? {
        log.error("🤨 INVALID: " + ex.message)
        return ex.message
    }
}