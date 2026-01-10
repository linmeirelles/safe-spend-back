package com.sistema.gastos.controller

import com.sistema.gastos.dto.category.CategoryRequest
import com.sistema.gastos.dto.category.CategoryResponse
import com.sistema.gastos.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categorias", description = "Gerenciamento de categorias de transações")
@SecurityRequirement(name = "bearer-jwt")
class CategoryController(
    private val categoryService: CategoryService
) {

    @PostMapping
    @Operation(summary = "Criar categoria", description = "Cria uma nova categoria")
    fun create(@Valid @RequestBody request: CategoryRequest): ResponseEntity<CategoryResponse> {
        val response = categoryService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    @Operation(summary = "Listar categorias", description = "Lista todas as categorias do usuário")
    fun findAll(): ResponseEntity<List<CategoryResponse>> {
        val response = categoryService.findAll()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria", description = "Busca uma categoria específica por ID")
    fun findById(@PathVariable id: UUID): ResponseEntity<CategoryResponse> {
        val response = categoryService.findById(id)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody request: CategoryRequest
    ): ResponseEntity<CategoryResponse> {
        val response = categoryService.update(id, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar categoria", description = "Remove uma categoria do sistema")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        categoryService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
