package com.sistema.gastos.dto.creditcard

import java.math.BigDecimal
import java.util.*

data class CreditCardResponse(
    val id: UUID,
    val name: String,
    val closingDay: Int,
    val dueDay: Int,
    val limitValue: BigDecimal,
    val usedLimit: BigDecimal,
    val availableLimit: BigDecimal
)
