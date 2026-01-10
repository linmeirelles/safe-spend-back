package com.sistema.gastos.security

import com.sistema.gastos.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("Usuário não encontrado com email: $email")
        
        return UserPrincipal.create(user)
    }

    @Transactional(readOnly = true)
    fun loadUserById(id: String): UserDetails {
        val user = userRepository.findById(java.util.UUID.fromString(id))
            .orElseThrow { UsernameNotFoundException("Usuário não encontrado com id: $id") }
        
        return UserPrincipal.create(user)
    }
}
