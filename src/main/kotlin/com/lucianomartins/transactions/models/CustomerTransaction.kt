package com.lucianomartins.transactions.models

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.GenerationType
import javax.persistence.JoinColumn
import javax.persistence.OneToOne


@Entity
@Table
data class CustomerTransaction (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @OneToOne
    @JoinColumn(name = "origin_account_id")
    val originAccount: Account,

    @OneToOne
    @JoinColumn(name = "destination_account_id")
    val destinationAccount: Account,

    val transactionValue: BigDecimal,

    val type: TransactionType,

    val completed: Boolean,

    val controlCode: UUID,

    val transactionDate: LocalDateTime
)