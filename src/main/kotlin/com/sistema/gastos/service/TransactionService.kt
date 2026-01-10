package com.sistema.gastos.service

import com.sistema.gastos.domain.repository.AccountRepository
import com.sistema.gastos.domain.repository.CategoryRepository
import com.sistema.gastos.domain.repository.CreditCardRepository
import com.sistema.gastos.domain.repository.TransactionRepository
import com.sistema.gastos.domain.repository.UserRepository
import com.sistema.gastos.dto.transaction.TransactionRequest
import com.sistema.gastos.dto.transaction.TransactionResponse
import com.sistema.gastos.exception.BusinessException
import com.sistema.gastos.exception.ResourceNotFoundException
import com.sistema.gastos.mapper.toEntity
import com.sistema.gastos.mapper.toResponse
import com.sistema.gastos.security.SecurityUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository,
    private val creditCardRepository: CreditCardRepository
) {

    @Transactional
    fun create(request: TransactionRequest): TransactionResponse {
        val userId = SecurityUtils.getCurrentUserId()
        
        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("Usuário não encontrado") }

        val category = categoryRepository.findByIdAndUserId(request.categoryId, userId)
            ?: throw ResourceNotFoundException("Categoria não encontrada")

        val account = request.accountId?.let {
            accountRepository.findByIdAndUserId(it, userId)
                ?: throw ResourceNotFoundException("Conta não encontrada")
        }

        val creditCard = request.creditCardId?.let {
            creditCardRepository.findByIdAndUserId(it, userId)
                ?: throw ResourceNotFoundException("Cartão de crédito não encontrado")
        }

        // Validação: deve ter conta OU cartão de crédito para despesas
        if (request.type.name == "EXPENSE" && account == null && creditCard == null) {
            throw BusinessException("Despesa deve estar vinculada a uma conta ou cartão de crédito")
        }

        val transaction = request.toEntity(user, category, account, creditCard)
        val savedTransaction = transactionRepository.save(transaction)

        return savedTransaction.toResponse()
    }

    @Transactional(readOnly = true)
    fun findAll(): List<TransactionResponse> {
        val userId = SecurityUtils.getCurrentUserId()
        return transactionRepository.findByUserId(userId).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun findById(id: UUID): TransactionResponse {
        val userId = SecurityUtils.getCurrentUserId()
        val transaction = transactionRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Transação não encontrada")

        return transaction.toResponse()
    }

    @Transactional(readOnly = true)
    fun findByPeriod(startDate: LocalDate, endDate: LocalDate): List<TransactionResponse> {
        val userId = SecurityUtils.getCurrentUserId()
        return transactionRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
            .map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun findPending(): List<TransactionResponse> {
        val userId = SecurityUtils.getCurrentUserId()
        return transactionRepository.findByUserIdAndPaid(userId, false)
            .map { it.toResponse() }
    }

    @Transactional
    fun update(id: UUID, request: TransactionRequest): TransactionResponse {
        val userId = SecurityUtils.getCurrentUserId()
        
        val existingTransaction = transactionRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Transação não encontrada")

        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("Usuário não encontrado") }

        val category = categoryRepository.findByIdAndUserId(request.categoryId, userId)
            ?: throw ResourceNotFoundException("Categoria não encontrada")

        val account = request.accountId?.let {
            accountRepository.findByIdAndUserId(it, userId)
                ?: throw ResourceNotFoundException("Conta não encontrada")
        }

        val creditCard = request.creditCardId?.let {
            creditCardRepository.findByIdAndUserId(it, userId)
                ?: throw ResourceNotFoundException("Cartão de crédito não encontrado")
        }

        val updatedTransaction = request.toEntity(user, category, account, creditCard)
            .copy(id = existingTransaction.id)
        val savedTransaction = transactionRepository.save(updatedTransaction)

        return savedTransaction.toResponse()
    }

    @Transactional
    fun delete(id: UUID) {
        val userId = SecurityUtils.getCurrentUserId()
        val transaction = transactionRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Transação não encontrada")

        transactionRepository.delete(transaction)
    }

    @Transactional
    fun markAsPaid(id: UUID): TransactionResponse {
        val userId = SecurityUtils.getCurrentUserId()
        val transaction = transactionRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Transação não encontrada")

        val updatedTransaction = transaction.copy(paid = true)
        val savedTransaction = transactionRepository.save(updatedTransaction)

        return savedTransaction.toResponse()
    }
}
