package com.sistema.gastos.dto.account

import com.sistema.gastos.domain.enum.AccountType
import java.math.BigDecimal
import java.util.*

data class AccountResponse(
    val id: UUID,
    val name: String,
    val initialBalance: BigDecimal,
    val currentBalance: BigDecimal,
    val type: AccountType
)
