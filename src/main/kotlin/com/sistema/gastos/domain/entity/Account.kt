package com.sistema.gastos.domain.entity

import com.sistema.gastos.domain.enum.AccountType
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "accounts")
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),
    
    @Column(nullable = false)
    val name: String,
    
    @Column(name = "initial_balance", nullable = false, precision = 19, scale = 2)
    val initialBalance: BigDecimal,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: AccountType,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    
    @OneToMany(mappedBy = "account", cascade = [CascadeType.ALL], orphanRemoval = true)
    val transactions: MutableList<Transaction> = mutableListOf()
)
