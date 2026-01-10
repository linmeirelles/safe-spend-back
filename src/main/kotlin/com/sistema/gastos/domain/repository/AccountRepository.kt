package com.sistema.gastos.domain.repository

import com.sistema.gastos.domain.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, UUID> {
    fun findByUserId(userId: UUID): List<Account>
    fun findByIdAndUserId(id: UUID, userId: UUID): Account?
    fun existsByIdAndUserId(id: UUID, userId: UUID): Boolean
}
