package com.lucianomartins.transactions.usecase

import com.lucianomartins.transactions.dto.CustomerResponse
import com.lucianomartins.transactions.repositories.CustomerRepository
import org.springframework.stereotype.Service

/**
 * UseCase that handles finding all customers
 *
 * @property customerRepository
 */
@Service
class FindAllCustomers(val customerRepository: CustomerRepository) {
    /**
     * find all customers
     *
     * @return List<CustomerResponse>
     */
    operator fun invoke(): List<CustomerResponse> {
        val customers = customerRepository.findAll().map { CustomerResponse.fromModel(it) }
        return if (customers.any()) {
            customers
        } else
            throw NoSuchElementException()
    }
}
