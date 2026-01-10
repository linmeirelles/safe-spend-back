package com.sistema.gastos.controller

import com.sistema.gastos.dto.transaction.TransactionRequest
import com.sistema.gastos.dto.transaction.TransactionResponse
import com.sistema.gastos.service.TransactionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transações", description = "Gerenciamento de transações financeiras")
@SecurityRequirement(name = "bearer-jwt")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping
    @Operation(summary = "Criar transação", description = "Registra uma nova transação")
    fun create(@Valid @RequestBody request: TransactionRequest): ResponseEntity<TransactionResponse> {
        val response = transactionService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    @Operation(summary = "Listar transações", description = "Lista todas as transações do usuário")
    fun findAll(): ResponseEntity<List<TransactionResponse>> {
        val response = transactionService.findAll()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar transação", description = "Busca uma transação específica por ID")
    fun findById(@PathVariable id: UUID): ResponseEntity<TransactionResponse> {
        val response = transactionService.findById(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/period")
    @Operation(
        summary = "Buscar transações por período",
        description = "Lista transações entre duas datas"
    )
    fun findByPeriod(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate
    ): ResponseEntity<List<TransactionResponse>> {
        val response = transactionService.findByPeriod(startDate, endDate)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/pending")
    @Operation(summary = "Listar transações pendentes", description = "Lista todas as transações não pagas")
    fun findPending(): ResponseEntity<List<TransactionResponse>> {
        val response = transactionService.findPending()
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar transação", description = "Atualiza os dados de uma transação")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody request: TransactionRequest
    ): ResponseEntity<TransactionResponse> {
        val response = transactionService.update(id, request)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{id}/mark-as-paid")
    @Operation(summary = "Marcar como paga", description = "Marca uma transação como paga")
    fun markAsPaid(@PathVariable id: UUID): ResponseEntity<TransactionResponse> {
        val response = transactionService.markAsPaid(id)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar transação", description = "Remove uma transação do sistema")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        transactionService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
