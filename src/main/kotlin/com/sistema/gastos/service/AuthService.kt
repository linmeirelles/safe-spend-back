package com.sistema.gastos.service

import com.sistema.gastos.config.JwtTokenProvider
import com.sistema.gastos.domain.entity.User
import com.sistema.gastos.domain.repository.UserRepository
import com.sistema.gastos.dto.auth.AuthResponse
import com.sistema.gastos.dto.auth.LoginRequest
import com.sistema.gastos.dto.auth.RegisterRequest
import com.sistema.gastos.exception.BusinessException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Locale
import java.util.Locale.getDefault

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager
) {

    @Transactional
    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw BusinessException("Email já está em uso")
        }

        val user = User(
            name = request.name,
            email = request.email.lowercase(getDefault()),
            password = passwordEncoder.encode(request.password)
        )

        val savedUser = userRepository.save(user)
        val token = jwtTokenProvider.generateToken(savedUser.id, savedUser.email)

        return AuthResponse(
            token = token,
            userId = savedUser.id,
            name = savedUser.name,
            email = savedUser.email
        )
    }

    @Transactional(readOnly = true)
    fun login(request: LoginRequest): AuthResponse {
        val email = request.email.lowercase(getDefault())
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, request.password)
        )

        val user = userRepository.findByEmail(email)
            ?: throw BusinessException("Usuário não encontrado")

        val token = jwtTokenProvider.generateToken(user.id, user.email)

        return AuthResponse(
            token = token,
            userId = user.id,
            name = user.name,
            email = user.email
        )
    }
}
