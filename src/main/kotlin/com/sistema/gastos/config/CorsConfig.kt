package com.sistema.gastos.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        
        // Permite requisições do frontend (ajuste a URL conforme necessário)
        configuration.allowedOrigins = listOf(
            "https://safe-spend-front.vercel.app",
            "http://localhost:3000",  // Next.js
            "http://localhost:5173",  // Vite
            "http://localhost:4200"   // Angular
        )
        
        // Permite todos os métodos HTTP
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        
        // Permite todos os headers
        configuration.allowedHeaders = listOf("*")
        
        // Permite envio de credenciais (cookies, authorization headers)
        configuration.allowCredentials = true
        
        // Expõe o header Authorization na resposta
        configuration.exposedHeaders = listOf("Authorization")
        
        // Cache da configuração CORS por 1 hora
        configuration.maxAge = 3600L
        
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        
        return source
    }
}
