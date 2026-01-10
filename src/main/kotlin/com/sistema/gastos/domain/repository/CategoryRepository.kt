package com.sistema.gastos.domain.repository

import com.sistema.gastos.domain.entity.Category
import com.sistema.gastos.domain.enum.CategoryType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository : JpaRepository<Category, UUID> {
    fun findByUserId(userId: UUID): List<Category>
    fun findByUserIdAndType(userId: UUID, type: CategoryType): List<Category>
    fun findByIdAndUserId(id: UUID, userId: UUID): Category?
    fun existsByIdAndUserId(id: UUID, userId: UUID): Boolean
}
