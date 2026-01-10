package com.sistema.gastos.domain.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "credit_cards")
data class CreditCard(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),
    
    @Column(nullable = false)
    val name: String,
    
    @Column(name = "closing_day", nullable = false)
    @Min(1)
    @Max(31)
    val closingDay: Int,
    
    @Column(name = "due_day", nullable = false)
    @Min(1)
    @Max(31)
    val dueDay: Int,
    
    @Column(name = "limit_value", nullable = false, precision = 19, scale = 2)
    val limitValue: BigDecimal,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    
    @OneToMany(mappedBy = "creditCard", cascade = [CascadeType.ALL], orphanRemoval = true)
    val transactions: MutableList<Transaction> = mutableListOf()
)
