package com.sistema.gastos.security

import com.sistema.gastos.domain.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserPrincipal(
    val id: UUID,
    val email: String,
    private val password: String
) : UserDetails {

    companion object {
        fun create(user: User): UserPrincipal {
            return UserPrincipal(
                id = user.id,
                email = user.email,
                password = user.password
            )
        }
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
