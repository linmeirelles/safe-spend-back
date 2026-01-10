package com.sistema.gastos.dto.category

import com.sistema.gastos.domain.enum.CategoryType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CategoryRequest(
    @field:NotBlank(message = "Nome é obrigatório")
    @field:Size(min = 2, max = 255, message = "Nome deve ter entre 2 e 255 caracteres")
    val name: String,
    
    val icon: String? = null,
    
    @field:NotNull(message = "Tipo é obrigatório")
    val type: CategoryType
)
