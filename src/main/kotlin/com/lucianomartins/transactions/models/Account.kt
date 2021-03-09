package com.lucianomartins.transactions.models

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
@Table
data class Account(
    @Id
    val id: String,

    @OneToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer,

    var balance: BigDecimal
) {
    fun canTransfer(intendedValue: BigDecimal) = balance - intendedValue >= BigDecimal.ZERO
    fun makeDebit(value: BigDecimal) {
        balance -= value
    }
    fun makeCredit(value: BigDecimal) {
        balance += value
    }
}