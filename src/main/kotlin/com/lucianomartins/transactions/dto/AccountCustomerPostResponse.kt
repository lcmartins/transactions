package com.lucianomartins.transactions.dto

import java.math.BigDecimal

data class AccountCustomerPostResponse (
    val customerId: String,
    val customerName: String,
    val accountId: String,
    val accountBalance: BigDecimal
)