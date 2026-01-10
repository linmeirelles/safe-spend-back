package com.sistema.gastos.domain.entity

import com.sistema.gastos.domain.enum.CategoryType
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),
    
    @Column(nullable = false)
    val name: String,
    
    @Column(nullable = true)
    val icon: String? = null,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: CategoryType,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    
    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], orphanRemoval = true)
    val transactions: MutableList<Transaction> = mutableListOf()
)
