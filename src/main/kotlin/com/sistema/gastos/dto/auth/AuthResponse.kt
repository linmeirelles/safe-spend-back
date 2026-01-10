package com.sistema.gastos.dto.auth

import java.util.*

data class AuthResponse(
    val token: String,
    val type: String = "Bearer",
    val userId: UUID,
    val name: String,
    val email: String
)
