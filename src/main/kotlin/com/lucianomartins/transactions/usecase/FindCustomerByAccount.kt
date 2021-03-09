package com.lucianomartins.transactions.usecase

import com.lucianomartins.transactions.dto.CustomerResponse
import com.lucianomartins.transactions.repositories.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * UseCase that find a customer by account
 *
 * @property accountRepository
 */
@Service
class FindCustomerByAccount(
    val accountRepository: AccountRepository
) {
    /**
     * find customer by an account id
     *
     * @param accountId
     * @return CustomerResponse
     */
    operator fun invoke(accountId: String): CustomerResponse {
        val account = accountRepository.findByIdOrNull(accountId)
        return if (account != null) {
            CustomerResponse.fromModel(account.customer)
        } else
            throw NoSuchElementException()
    }
}