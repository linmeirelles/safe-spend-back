package com.sistema.gastos.dto.creditcard

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class CreditCardRequest(
    @field:NotBlank(message = "Nome é obrigatório")
    @field:Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
    val name: String,
    
    @field:NotNull(message = "Dia de fechamento é obrigatório")
    @field:Min(value = 1, message = "Dia de fechamento deve ser entre 1 e 31")
    @field:Max(value = 31, message = "Dia de fechamento deve ser entre 1 e 31")
    val closingDay: Int,
    
    @field:NotNull(message = "Dia de vencimento é obrigatório")
    @field:Min(value = 1, message = "Dia de vencimento deve ser entre 1 e 31")
    @field:Max(value = 31, message = "Dia de vencimento deve ser entre 1 e 31")
    val dueDay: Int,
    
    @field:NotNull(message = "Limite é obrigatório")
    val limitValue: BigDecimal
)
