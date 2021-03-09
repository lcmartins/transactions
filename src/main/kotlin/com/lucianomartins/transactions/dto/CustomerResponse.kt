package com.lucianomartins.transactions.dto

import com.lucianomartins.transactions.models.Customer

class CustomerResponse(
    val id: String,
    val name: String
) {
    companion object {
        fun fromModel(model: Customer) = CustomerResponse(model.id, model.name)
    }
}