package com.lucianomartins.transactions.usecase

import com.lucianomartins.transactions.dto.CustomerTransactionResponse
import com.lucianomartins.transactions.repositories.AccountRepository
import com.lucianomartins.transactions.repositories.CustomerTransactionRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * UseCase that find transactions by account
 *
 * @property accountRepository
 * @property transactionRepository
 */
@Service
class FindTransactionsByAccount(
    val accountRepository: AccountRepository,
    val transactionRepository: CustomerTransactionRepository
) {

    /**
     * retrieve a list of transactions by account
     *
     * @param accountId
     * @return List<CustomerTransactionResponse>
     */
    operator fun invoke(accountId: String): List<CustomerTransactionResponse> {
        val account = accountRepository.findByIdOrNull(accountId) ?: throw NoSuchElementException()
        val transactions = transactionRepository.findByOriginAccount(account)
        if (transactions.isEmpty()) {
            throw NoSuchElementException()
        }
        return transactions.map { CustomerTransactionResponse.mapFromModel(it) }
    }
}