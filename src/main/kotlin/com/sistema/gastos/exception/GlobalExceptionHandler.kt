package com.sistema.gastos.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.net.URI
import java.time.Instant

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message ?: "Recurso não encontrado")
        problemDetail.title = "Recurso não encontrado"
        problemDetail.type = URI.create("https://api.sistema-gastos.com/errors/not-found")
        problemDetail.setProperty("timestamp", Instant.now())
        return problemDetail
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.message ?: "Não autorizado")
        problemDetail.title = "Não autorizado"
        problemDetail.type = URI.create("https://api.sistema-gastos.com/errors/unauthorized")
        problemDetail.setProperty("timestamp", Instant.now())
        return problemDetail
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(ex: BusinessException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.message ?: "Erro de negócio")
        problemDetail.title = "Erro de negócio"
        problemDetail.type = URI.create("https://api.sistema-gastos.com/errors/business")
        problemDetail.setProperty("timestamp", Instant.now())
        return problemDetail
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(ex: BadCredentialsException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos")
        problemDetail.title = "Credenciais inválidas"
        problemDetail.type = URI.create("https://api.sistema-gastos.com/errors/bad-credentials")
        problemDetail.setProperty("timestamp", Instant.now())
        return problemDetail
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFoundException(ex: UsernameNotFoundException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message ?: "Usuário não encontrado")
        problemDetail.title = "Usuário não encontrado"
        problemDetail.type = URI.create("https://api.sistema-gastos.com/errors/user-not-found")
        problemDetail.setProperty("timestamp", Instant.now())
        return problemDetail
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ProblemDetail {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Inválido") }
        
        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "Erro de validação nos campos"
        )
        problemDetail.title = "Erro de validação"
        problemDetail.type = URI.create("https://api.sistema-gastos.com/errors/validation")
        problemDetail.setProperty("timestamp", Instant.now())
        problemDetail.setProperty("errors", errors)
        return problemDetail
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Erro interno no servidor"
        )
        problemDetail.title = "Erro interno"
        problemDetail.type = URI.create("https://api.sistema-gastos.com/errors/internal")
        problemDetail.setProperty("timestamp", Instant.now())
        problemDetail.setProperty("message", ex.message)
        return problemDetail
    }
}
