package com.lucianomartins.transactions.dto

import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class AccountCustomerPostRequest(
    @get:NotBlank
    val customerId: String,

    @get:NotBlank
    val customerName: String,

    @get:NotBlank
    val accountId: String,

    @get:NotNull
    val accountBalance: BigDecimal
)