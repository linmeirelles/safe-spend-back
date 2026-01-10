package com.sistema.gastos.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secret: String = "",
    val expiration: Long = 86400000L // 24 horas
)
