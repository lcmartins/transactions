package com.lucianomartins.transactions.repositories

import com.lucianomartins.transactions.models.Account
import com.lucianomartins.transactions.models.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepository: CrudRepository<Customer, String> {
}