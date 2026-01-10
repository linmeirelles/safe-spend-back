package com.sistema.gastos.service

import com.sistema.gastos.domain.repository.AccountRepository
import com.sistema.gastos.domain.repository.TransactionRepository
import com.sistema.gastos.domain.repository.UserRepository
import com.sistema.gastos.dto.account.AccountRequest
import com.sistema.gastos.dto.account.AccountResponse
import com.sistema.gastos.exception.ResourceNotFoundException
import com.sistema.gastos.mapper.toEntity
import com.sistema.gastos.mapper.toResponse
import com.sistema.gastos.security.SecurityUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) {

    @Transactional
    fun create(request: AccountRequest): AccountResponse {
        val userId = SecurityUtils.getCurrentUserId()
        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("Usuário não encontrado") }

        val account = request.toEntity(user)
        val savedAccount = accountRepository.save(account)

        return savedAccount.toResponse(savedAccount.initialBalance)
    }

    @Transactional(readOnly = true)
    fun findAll(): List<AccountResponse> {
        val userId = SecurityUtils.getCurrentUserId()
        val accounts = accountRepository.findByUserId(userId)

        return accounts.map { account ->
            val currentBalance = calculateCurrentBalance(account.id, account.initialBalance)
            account.toResponse(currentBalance)
        }
    }

    @Transactional(readOnly = true)
    fun findById(id: UUID): AccountResponse {
        val userId = SecurityUtils.getCurrentUserId()
        val account = accountRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Conta não encontrada")

        val currentBalance = calculateCurrentBalance(account.id, account.initialBalance)
        return account.toResponse(currentBalance)
    }

    @Transactional
    fun update(id: UUID, request: AccountRequest): AccountResponse {
        val userId = SecurityUtils.getCurrentUserId()
        val existingAccount = accountRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Conta não encontrada")

        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("Usuário não encontrado") }

        val updatedAccount = request.toEntity(user).copy(id = existingAccount.id)
        val savedAccount = accountRepository.save(updatedAccount)

        val currentBalance = calculateCurrentBalance(savedAccount.id, savedAccount.initialBalance)
        return savedAccount.toResponse(currentBalance)
    }

    @Transactional
    fun delete(id: UUID) {
        val userId = SecurityUtils.getCurrentUserId()
        val account = accountRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Conta não encontrada")

        accountRepository.delete(account)
    }

    private fun calculateCurrentBalance(accountId: UUID, initialBalance: BigDecimal): BigDecimal {
        val userId = SecurityUtils.getCurrentUserId()
        val transactions = transactionRepository.findByUserIdAndAccountId(userId, accountId)

        val totalIncome = transactions
            .filter { it.type.name == "INCOME" && it.paid }
            .sumOf { it.amount }

        val totalExpense = transactions
            .filter { it.type.name == "EXPENSE" && it.paid }
            .sumOf { it.amount }

        return initialBalance + totalIncome - totalExpense
    }
}
