package com.sistema.gastos.dto.account

import com.sistema.gastos.domain.enum.AccountType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class AccountRequest(
    @field:NotBlank(message = "Nome é obrigatório")
    @field:Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
    val name: String,
    
    @field:NotNull(message = "Saldo inicial é obrigatório")
    val initialBalance: BigDecimal,
    
    @field:NotNull(message = "Tipo é obrigatório")
    val type: AccountType
)
