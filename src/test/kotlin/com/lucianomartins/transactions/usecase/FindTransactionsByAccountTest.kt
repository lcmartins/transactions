package com.lucianomartins.transactions.usecase

import com.lucianomartins.transactions.mocks.MockRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.NoSuchElementException

@ExtendWith(SpringExtension::class)
class FindTransactionsByAccountTest {

    @Test
    fun `given the account passed does not exists an exception must be thrown`() {
        MockRepository.accountFindByIdReturnsNull()
        val executableUseCase = FindTransactionsByAccount(
            MockRepository.accountRepository,
            MockRepository.transactionRepository
        )
        assertThrows<NoSuchElementException> {
            executableUseCase("account_1")
        }
    }

    @Test
    fun `given the account passed has no transactions an exception must be thrown`() {
        MockRepository.accountFindByIdReturnsMock()
        MockRepository.transactionFindByOriginAccountReturnsEmpty()
        val executableUseCase = FindTransactionsByAccount(
            MockRepository.accountRepository,
            MockRepository.transactionRepository
        )
        assertThrows<NoSuchElementException> {
            executableUseCase("account_1")
        }
    }

    @Test
    fun `given no exceptions are throw, a list must be returned`() {
        MockRepository.accountFindByIdReturnsMock()
        MockRepository.transactionFindByOriginAccountReturnsList()
        val executableUseCase = FindTransactionsByAccount(
            MockRepository.accountRepository,
            MockRepository.transactionRepository
        )
        val transactions = executableUseCase("account_1")
        Assertions.assertTrue(transactions.isNotEmpty())
    }

}