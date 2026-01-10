package com.sistema.gastos.mapper

import com.sistema.gastos.domain.entity.Category
import com.sistema.gastos.domain.entity.User
import com.sistema.gastos.dto.category.CategoryRequest
import com.sistema.gastos.dto.category.CategoryResponse

fun CategoryRequest.toEntity(user: User): Category {
    return Category(
        name = this.name,
        icon = this.icon,
        type = this.type,
        user = user
    )
}

fun Category.toResponse(): CategoryResponse {
    return CategoryResponse(
        id = this.id,
        name = this.name,
        icon = this.icon,
        type = this.type
    )
}
