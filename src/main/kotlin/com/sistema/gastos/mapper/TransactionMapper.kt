package com.sistema.gastos.mapper

import com.sistema.gastos.domain.entity.Account
import com.sistema.gastos.domain.entity.Category
import com.sistema.gastos.domain.entity.CreditCard
import com.sistema.gastos.domain.entity.Transaction
import com.sistema.gastos.domain.entity.User
import com.sistema.gastos.dto.transaction.TransactionRequest
import com.sistema.gastos.dto.transaction.TransactionResponse

fun TransactionRequest.toEntity(
    user: User,
    category: Category,
    account: Account?,
    creditCard: CreditCard?
): Transaction {
    return Transaction(
        description = this.description,
        amount = this.amount,
        date = this.date,
        paid = this.paid,
        type = this.type,
        category = category,
        account = account,
        creditCard = creditCard,
        user = user,
        installmentCurrent = this.installmentCurrent,
        installmentTotal = this.installmentTotal
    )
}

fun Transaction.toResponse(): TransactionResponse {
    return TransactionResponse(
        id = this.id,
        description = this.description,
        amount = this.amount,
        date = this.date,
        paid = this.paid,
        type = this.type,
        categoryId = this.category.id,
        categoryName = this.category.name,
        accountId = this.account?.id,
        accountName = this.account?.name,
        creditCardId = this.creditCard?.id,
        creditCardName = this.creditCard?.name,
        installmentCurrent = this.installmentCurrent,
        installmentTotal = this.installmentTotal
    )
}
