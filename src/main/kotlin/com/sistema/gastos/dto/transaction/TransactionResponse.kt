package com.sistema.gastos.dto.transaction

import com.sistema.gastos.domain.enum.TransactionType
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class TransactionResponse(
    val id: UUID,
    val description: String,
    val amount: BigDecimal,
    val date: LocalDate,
    val paid: Boolean,
    val type: TransactionType,
    val categoryId: UUID,
    val categoryName: String,
    val accountId: UUID?,
    val accountName: String?,
    val creditCardId: UUID?,
    val creditCardName: String?,
    val installmentCurrent: Int?,
    val installmentTotal: Int?
)
