package com.lucianomartins.transactions.dto

import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class TransferPutRequest(
    @get:NotBlank
    val originAccountId: String,

    @get:NotBlank
    val destinationAccountId: String,

    @get:NotNull
    val intendedValue: BigDecimal
)