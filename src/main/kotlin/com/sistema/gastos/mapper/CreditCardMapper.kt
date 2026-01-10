package com.sistema.gastos.mapper

import com.sistema.gastos.domain.entity.CreditCard
import com.sistema.gastos.domain.entity.User
import com.sistema.gastos.dto.creditcard.CreditCardRequest
import com.sistema.gastos.dto.creditcard.CreditCardResponse
import java.math.BigDecimal

fun CreditCardRequest.toEntity(user: User): CreditCard {
    return CreditCard(
        name = this.name,
        closingDay = this.closingDay,
        dueDay = this.dueDay,
        limitValue = this.limitValue,
        user = user
    )
}

fun CreditCard.toResponse(usedLimit: BigDecimal): CreditCardResponse {
    return CreditCardResponse(
        id = this.id,
        name = this.name,
        closingDay = this.closingDay,
        dueDay = this.dueDay,
        limitValue = this.limitValue,
        usedLimit = usedLimit,
        availableLimit = this.limitValue - usedLimit
    )
}
