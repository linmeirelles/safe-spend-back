package com.sistema.gastos.domain.repository

import com.sistema.gastos.domain.entity.CreditCard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CreditCardRepository : JpaRepository<CreditCard, UUID> {
    fun findByUserId(userId: UUID): List<CreditCard>
    fun findByIdAndUserId(id: UUID, userId: UUID): CreditCard?
    fun existsByIdAndUserId(id: UUID, userId: UUID): Boolean
}
