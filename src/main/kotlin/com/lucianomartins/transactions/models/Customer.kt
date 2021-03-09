package com.lucianomartins.transactions.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "customer")
data class Customer (
    @Id
    val id: String,

    val name: String
)