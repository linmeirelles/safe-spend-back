package com.sistema.gastos.controller

import com.sistema.gastos.dto.account.AccountRequest
import com.sistema.gastos.dto.account.AccountResponse
import com.sistema.gastos.service.AccountService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Contas", description = "Gerenciamento de contas/carteiras")
@SecurityRequirement(name = "bearer-jwt")
class AccountController(
    private val accountService: AccountService
) {

    @PostMapping
    @Operation(summary = "Criar conta", description = "Cria uma nova conta/carteira")
    fun create(@Valid @RequestBody request: AccountRequest): ResponseEntity<AccountResponse> {
        val response = accountService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    @Operation(summary = "Listar contas", description = "Lista todas as contas do usuário autenticado")
    fun findAll(): ResponseEntity<List<AccountResponse>> {
        val response = accountService.findAll()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta", description = "Busca uma conta específica por ID")
    fun findById(@PathVariable id: UUID): ResponseEntity<AccountResponse> {
        val response = accountService.findById(id)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conta", description = "Atualiza os dados de uma conta")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody request: AccountRequest
    ): ResponseEntity<AccountResponse> {
        val response = accountService.update(id, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar conta", description = "Remove uma conta do sistema")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        accountService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
