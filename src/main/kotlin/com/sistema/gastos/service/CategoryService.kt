package com.sistema.gastos.service

import com.sistema.gastos.domain.repository.CategoryRepository
import com.sistema.gastos.domain.repository.UserRepository
import com.sistema.gastos.dto.category.CategoryRequest
import com.sistema.gastos.dto.category.CategoryResponse
import com.sistema.gastos.exception.ResourceNotFoundException
import com.sistema.gastos.mapper.toEntity
import com.sistema.gastos.mapper.toResponse
import com.sistema.gastos.security.SecurityUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun create(request: CategoryRequest): CategoryResponse {
        val userId = SecurityUtils.getCurrentUserId()
        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("Usuário não encontrado") }

        val category = request.toEntity(user)
        val savedCategory = categoryRepository.save(category)

        return savedCategory.toResponse()
    }

    @Transactional(readOnly = true)
    fun findAll(): List<CategoryResponse> {
        val userId = SecurityUtils.getCurrentUserId()
        return categoryRepository.findByUserId(userId).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun findById(id: UUID): CategoryResponse {
        val userId = SecurityUtils.getCurrentUserId()
        val category = categoryRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Categoria não encontrada")

        return category.toResponse()
    }

    @Transactional
    fun update(id: UUID, request: CategoryRequest): CategoryResponse {
        val userId = SecurityUtils.getCurrentUserId()
        val existingCategory = categoryRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Categoria não encontrada")

        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("Usuário não encontrado") }

        val updatedCategory = request.toEntity(user).copy(id = existingCategory.id)
        val savedCategory = categoryRepository.save(updatedCategory)

        return savedCategory.toResponse()
    }

    @Transactional
    fun delete(id: UUID) {
        val userId = SecurityUtils.getCurrentUserId()
        val category = categoryRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Categoria não encontrada")

        categoryRepository.delete(category)
    }
}
