package com.sistema.gastos.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),
    
    @Column(nullable = false)
    val name: String,
    
    @Column(nullable = false, unique = true)
    val email: String,
    
    @Column(nullable = false)
    val password: String,
    
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val accounts: MutableList<Account> = mutableListOf(),
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val categories: MutableList<Category> = mutableListOf(),
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val creditCards: MutableList<CreditCard> = mutableListOf(),
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val transactions: MutableList<Transaction> = mutableListOf()
)
