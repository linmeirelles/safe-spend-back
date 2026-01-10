package com.sistema.gastos.mapper

import com.sistema.gastos.domain.entity.Account
import com.sistema.gastos.domain.entity.User
import com.sistema.gastos.dto.account.AccountRequest
import com.sistema.gastos.dto.account.AccountResponse
import java.math.BigDecimal

fun AccountRequest.toEntity(user: User): Account {
    return Account(
        name = this.name,
        initialBalance = this.initialBalance,
        type = this.type,
        user = user
    )
}

fun Account.toResponse(currentBalance: BigDecimal): AccountResponse {
    return AccountResponse(
        id = this.id,
        name = this.name,
        initialBalance = this.initialBalance,
        currentBalance = currentBalance,
        type = this.type
    )
}
