package com.sistema.gastos.domain.repository

import com.sistema.gastos.domain.entity.Transaction
import com.sistema.gastos.domain.enum.TransactionType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface TransactionRepository : JpaRepository<Transaction, UUID> {
    fun findByUserId(userId: UUID): List<Transaction>
    fun findByIdAndUserId(id: UUID, userId: UUID): Transaction?
    fun existsByIdAndUserId(id: UUID, userId: UUID): Boolean
    fun findByUserIdAndDateBetween(userId: UUID, startDate: LocalDate, endDate: LocalDate): List<Transaction>
    fun findByUserIdAndType(userId: UUID, type: TransactionType): List<Transaction>
    fun findByUserIdAndAccountId(userId: UUID, accountId: UUID): List<Transaction>
    fun findByUserIdAndCreditCardId(userId: UUID, creditCardId: UUID): List<Transaction>
    fun findByUserIdAndCategoryId(userId: UUID, categoryId: UUID): List<Transaction>
    fun findByUserIdAndPaid(userId: UUID, paid: Boolean): List<Transaction>
}
