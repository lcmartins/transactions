package com.lucianomartins.transactions.usecase

import com.lucianomartins.transactions.models.Account
import com.lucianomartins.transactions.models.Customer
import com.lucianomartins.transactions.repositories.AccountRepository
import com.lucianomartins.transactions.repositories.CustomerRepository
import com.lucianomartins.transactions.dto.AccountCustomerPostRequest
import com.lucianomartins.transactions.dto.AccountCustomerPostResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.UnsupportedOperationException
import javax.transaction.Transactional

@Service
class OpenAccount(
    val customerRepository: CustomerRepository,
    val accountRepository: AccountRepository
) {

    @Transactional
    operator fun invoke(postObjectAccount: AccountCustomerPostRequest): AccountCustomerPostResponse {
        val existingCustomer = customerRepository.findByIdOrNull(postObjectAccount.customerId)
        val existingAccount = accountRepository.findByIdOrNull(postObjectAccount.accountId)

        if (existingAccount != null) {
            throw UnsupportedOperationException("account number must be unique!!!")
        }

        return if (existingCustomer != null) {
            val account = Account(postObjectAccount.accountId, existingCustomer, postObjectAccount.accountBalance)
            createResponse(accountRepository.save(account))
        } else {
            val customer = Customer(postObjectAccount.customerId, postObjectAccount.customerName)
            customerRepository.save(customer)
            val account = Account(postObjectAccount.accountId, customer, postObjectAccount.accountBalance)
            createResponse(accountRepository.save(account))
        }
    }

    private fun createResponse(account: Account): AccountCustomerPostResponse {
        return AccountCustomerPostResponse(account.customer.id, account.customer.name, account.id, account.balance)
    }
}