package com.sistema.gastos.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    private val key: Key by lazy {
        Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())
    }

    fun generateToken(userId: UUID, email: String): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtProperties.expiration)

        return Jwts.builder()
            .setSubject(userId.toString())
            .claim("email", email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key)
            .compact()
    }

    fun getUserIdFromToken(token: String): UUID {
        val claims = getClaimsFromToken(token)
        return UUID.fromString(claims.subject)
    }

    fun getEmailFromToken(token: String): String {
        val claims = getClaimsFromToken(token)
        return claims["email"] as String
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaimsFromToken(token)
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    private fun getClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}
