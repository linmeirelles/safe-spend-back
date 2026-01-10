package com.sistema.gastos.dto.category

import com.sistema.gastos.domain.enum.CategoryType
import java.util.*

data class CategoryResponse(
    val id: UUID,
    val name: String,
    val icon: String?,
    val type: CategoryType
)
