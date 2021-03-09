package com.lucianomartins.transactions.repositories

import com.lucianomartins.transactions.models.Account
import com.lucianomartins.transactions.models.CustomerTransaction
import org.springframework.data.repository.CrudRepository

interface CustomerTransactionRepository: CrudRepository<CustomerTransaction, Int> {
    fun findByOriginAccount(account: Account): List<CustomerTransaction>
}