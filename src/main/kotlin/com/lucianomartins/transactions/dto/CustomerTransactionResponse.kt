package com.lucianomartins.transactions.dto

import com.lucianomartins.transactions.models.CustomerTransaction
import java.math.BigDecimal
import java.time.LocalDateTime

class CustomerTransactionResponse(
    val originAccountId: String,
    val destinationAccountId: String,
    val type: String,
    val completed: Boolean,
    val transactionValue: BigDecimal,
    val controlCode: String,
    val transactionDate: LocalDateTime
) {
    companion object {
        fun mapFromModel(customerTransaction: CustomerTransaction): CustomerTransactionResponse {
            return CustomerTransactionResponse(
                customerTransaction.originAccount.id,
                customerTransaction.destinationAccount.id,
                customerTransaction.type.name,
                customerTransaction.completed,
                customerTransaction.transactionValue,
                customerTransaction.controlCode.toString(),
                customerTransaction.transactionDate
            )
        }
    }
}