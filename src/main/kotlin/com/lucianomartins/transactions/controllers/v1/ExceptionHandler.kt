package com.lucianomartins.transactions.controllers.v1

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.UnsupportedOperationException

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(value = [UnsupportedOperationException::class])
    fun handleUnsupportedOperationException(exception: Throwable): ResponseEntity<HttpStatus> =
        status(HttpStatus.BAD_REQUEST).build()

    @ExceptionHandler(value = [NoSuchElementException::class])
    fun handleNoSuchElementException(exception: Throwable): ResponseEntity<HttpStatus> =
        status(HttpStatus.NOT_FOUND).build()
}