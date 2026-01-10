package com.sistema.gastos.service

import com.sistema.gastos.domain.repository.CreditCardRepository
import com.sistema.gastos.domain.repository.TransactionRepository
import com.sistema.gastos.domain.repository.UserRepository
import com.sistema.gastos.dto.creditcard.CreditCardRequest
import com.sistema.gastos.dto.creditcard.CreditCardResponse
import com.sistema.gastos.exception.ResourceNotFoundException
import com.sistema.gastos.mapper.toEntity
import com.sistema.gastos.mapper.toResponse
import com.sistema.gastos.security.SecurityUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
class CreditCardService(
    private val creditCardRepository: CreditCardRepository,
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) {

    @Transactional
    fun create(request: CreditCardRequest): CreditCardResponse {
        val userId = SecurityUtils.getCurrentUserId()
        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("Usuário não encontrado") }

        val creditCard = request.toEntity(user)
        val savedCreditCard = creditCardRepository.save(creditCard)

        return savedCreditCard.toResponse(BigDecimal.ZERO)
    }

    @Transactional(readOnly = true)
    fun findAll(): List<CreditCardResponse> {
        val userId = SecurityUtils.getCurrentUserId()
        val creditCards = creditCardRepository.findByUserId(userId)

        return creditCards.map { creditCard ->
            val usedLimit = calculateUsedLimit(creditCard.id)
            creditCard.toResponse(usedLimit)
        }
    }

    @Transactional(readOnly = true)
    fun findById(id: UUID): CreditCardResponse {
        val userId = SecurityUtils.getCurrentUserId()
        val creditCard = creditCardRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Cartão de crédito não encontrado")

        val usedLimit = calculateUsedLimit(creditCard.id)
        return creditCard.toResponse(usedLimit)
    }

    @Transactional
    fun update(id: UUID, request: CreditCardRequest): CreditCardResponse {
        val userId = SecurityUtils.getCurrentUserId()
        val existingCreditCard = creditCardRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Cartão de crédito não encontrado")

        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("Usuário não encontrado") }

        val updatedCreditCard = request.toEntity(user).copy(id = existingCreditCard.id)
        val savedCreditCard = creditCardRepository.save(updatedCreditCard)

        val usedLimit = calculateUsedLimit(savedCreditCard.id)
        return savedCreditCard.toResponse(usedLimit)
    }

    @Transactional
    fun delete(id: UUID) {
        val userId = SecurityUtils.getCurrentUserId()
        val creditCard = creditCardRepository.findByIdAndUserId(id, userId)
            ?: throw ResourceNotFoundException("Cartão de crédito não encontrado")

        creditCardRepository.delete(creditCard)
    }

    private fun calculateUsedLimit(creditCardId: UUID): BigDecimal {
        val userId = SecurityUtils.getCurrentUserId()
        val transactions = transactionRepository.findByUserIdAndCreditCardId(userId, creditCardId)

        return transactions
            .filter { !it.paid }
            .sumOf { it.amount }
    }
}
