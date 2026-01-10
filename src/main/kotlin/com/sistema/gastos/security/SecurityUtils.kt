package com.sistema.gastos.security

import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

object SecurityUtils {
    fun getCurrentUserId(): UUID {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication?.principal is UserPrincipal) {
            val userPrincipal = authentication.principal as UserPrincipal
            return userPrincipal.id
        }
        throw IllegalStateException("Usuário não autenticado")
    }

    fun getCurrentUserPrincipal(): UserPrincipal {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication?.principal is UserPrincipal) {
            return authentication.principal as UserPrincipal
        }
        throw IllegalStateException("Usuário não autenticado")
    }
}
