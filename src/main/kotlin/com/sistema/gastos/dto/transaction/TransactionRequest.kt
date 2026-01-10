package com.sistema.gastos.dto.transaction

import com.sistema.gastos.domain.enum.TransactionType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class TransactionRequest(
    @field:NotBlank(message = "Descrição é obrigatória")
    @field:Size(min = 3, max = 255, message = "Descrição deve ter entre 3 e 255 caracteres")
    val description: String,
    
    @field:NotNull(message = "Valor é obrigatório")
    @field:Positive(message = "Valor deve ser positivo")
    val amount: BigDecimal,
    
    @field:NotNull(message = "Data é obrigatória")
    val date: LocalDate,
    
    @field:NotNull(message = "Status de pagamento é obrigatório")
    val paid: Boolean,
    
    @field:NotNull(message = "Tipo é obrigatório")
    val type: TransactionType,
    
    @field:NotNull(message = "Categoria é obrigatória")
    val categoryId: UUID,
    
    val accountId: UUID? = null,
    
    val creditCardId: UUID? = null,
    
    val installmentCurrent: Int? = null,
    
    val installmentTotal: Int? = null
)
