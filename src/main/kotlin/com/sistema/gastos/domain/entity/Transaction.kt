package com.sistema.gastos.domain.entity

import com.sistema.gastos.domain.enum.TransactionType
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),
    
    @Column(nullable = false)
    val description: String,
    
    @Column(nullable = false, precision = 19, scale = 2)
    val amount: BigDecimal,
    
    @Column(nullable = false)
    val date: LocalDate,
    
    @Column(nullable = false)
    val paid: Boolean = false,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: TransactionType,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = true)
    val account: Account? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id", nullable = true)
    val creditCard: CreditCard? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    
    @Column(name = "installment_current", nullable = true)
    val installmentCurrent: Int? = null,
    
    @Column(name = "installment_total", nullable = true)
    val installmentTotal: Int? = null
) {
    @Column(name = "category_id", insertable = false, updatable = false)
    val categoryId: UUID? = null
    
    @Column(name = "account_id", insertable = false, updatable = false)
    val accountId: UUID? = null
    
    @Column(name = "credit_card_id", insertable = false, updatable = false)
    val creditCardId: UUID? = null
    
    @Column(name = "user_id", insertable = false, updatable = false)
    val userId: UUID? = null
}
