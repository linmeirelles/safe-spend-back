package com.sistema.gastos.controller

import com.sistema.gastos.dto.creditcard.CreditCardRequest
import com.sistema.gastos.dto.creditcard.CreditCardResponse
import com.sistema.gastos.service.CreditCardService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/credit-cards")
@Tag(name = "Cartões de Crédito", description = "Gerenciamento de cartões de crédito")
@SecurityRequirement(name = "bearer-jwt")
class CreditCardController(
    private val creditCardService: CreditCardService
) {

    @PostMapping
    @Operation(summary = "Criar cartão", description = "Cadastra um novo cartão de crédito")
    fun create(@Valid @RequestBody request: CreditCardRequest): ResponseEntity<CreditCardResponse> {
        val response = creditCardService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    @Operation(summary = "Listar cartões", description = "Lista todos os cartões de crédito do usuário")
    fun findAll(): ResponseEntity<List<CreditCardResponse>> {
        val response = creditCardService.findAll()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cartão", description = "Busca um cartão de crédito específico por ID")
    fun findById(@PathVariable id: UUID): ResponseEntity<CreditCardResponse> {
        val response = creditCardService.findById(id)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cartão", description = "Atualiza os dados de um cartão de crédito")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody request: CreditCardRequest
    ): ResponseEntity<CreditCardResponse> {
        val response = creditCardService.update(id, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cartão", description = "Remove um cartão de crédito do sistema")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        creditCardService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
