package com.sistema.gastos

import com.sistema.gastos.config.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class)
class SistemaGastosApplication

fun main(args: Array<String>) {
    runApplication<SistemaGastosApplication>(*args)
}
