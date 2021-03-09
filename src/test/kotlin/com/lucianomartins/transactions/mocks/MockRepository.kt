package com.lucianomartins.transactions.mocks

import com.lucianomartins.transactions.models.Account
import com.lucianomartins.transactions.models.CustomerTransaction
import com.lucianomartins.transactions.models.TransactionType
import com.lucianomartins.transactions.repositories.AccountRepository
import com.lucianomartins.transactions.repositories.CustomerRepository
import com.lucianomartins.transactions.repositories.CustomerTransactionRepository
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class MockRepository {
    companion object {
        val accountRepository = mockk<AccountRepository>()
        val transactionRepository = mockk<CustomerTransactionRepository>()
        val customerRepository = mockk<CustomerRepository>()

        fun accountFindByIdReturnsNull() {
            every {
                accountRepository.findByIdOrNull(any())
            } returns null
        }

        fun accountFindByIdReturnsMock() {
            every {
                accountRepository.findByIdOrNull(any())
            } returns Mock.createAccountMock()
        }

        fun findAllCustomersReturnsMock() {
            every {
                customerRepository.findAll()
            } returns listOf(Mock.createCustomerMock())
        }

        fun findAllCustomersReturnsEmpty() {
            every {
                customerRepository.findAll()
            } returns emptyList()
        }

        fun accountFindByIdOrNullReturnsAccount(account: Account) {
            every {
                accountRepository.findByIdOrNull(account.id)
            } returns account
        }

        fun accountSaveReturnsMock() {
            every {
                accountRepository.save(any())
            } returns Mock.createAccountMock()
        }

        fun mockSaveTransaction() {
            val originAccount = Mock.createAccountWithMoreThan1000L()
            val destinationAccount = Mock.createAccountWithLessThan1000L()

            every {
                transactionRepository.save(any())
            } returns Mock.createTransactionMock(originAccount, destinationAccount)
        }


        fun transactionFindByOriginAccountReturnsEmpty() {
            every {
                transactionRepository.findByOriginAccount(any())
            } returns emptyList()
        }

        fun transactionFindByOriginAccountReturnsList() {
            val originAccount = Mock.createAccountWithMoreThan1000L()
            val destinationAccount = Mock.createAccountWithLessThan1000L()
            every {
                transactionRepository.findByOriginAccount(any())
            } returns listOf(Mock.createTransactionMock(originAccount, destinationAccount))
        }
    }
}