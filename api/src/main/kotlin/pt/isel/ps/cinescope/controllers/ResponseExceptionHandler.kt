package pt.isel.ps.cinescope.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pt.isel.ps.cinescope.controllers.models.ProblemJsonModel
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.services.exceptions.NotFoundException
import pt.isel.ps.cinescope.services.exceptions.UnauthorizedException

@ControllerAdvice
class ResponseExceptionHandler: ResponseEntityExceptionHandler(){

    @ExceptionHandler(value = [NotFoundException::class])
    fun exceptionHandler(exception: NotFoundException) = ResponseEntity
        .status(404)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(ProblemJsonModel("https://example.org/problems/not-found", exception.msg))

    @ExceptionHandler(value = [BadRequestException::class])
    fun exceptionHandler(exception: BadRequestException) = ResponseEntity
        .status(400)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(ProblemJsonModel("https://example.org/problems/bad-request", exception.msg))

    @ExceptionHandler(value = [UnauthorizedException::class])
    fun exceptionHandler(exception: UnauthorizedException) = ResponseEntity
        .status(401)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(ProblemJsonModel("https://example.org/problems/unauthorized", exception.msg))
}