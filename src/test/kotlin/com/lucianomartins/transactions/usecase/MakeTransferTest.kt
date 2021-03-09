package com.lucianomartins.transactions.usecase

import com.lucianomartins.transactions.mocks.Mock
import com.lucianomartins.transactions.mocks.MockRepository
import com.lucianomartins.transactions.models.Account
import com.lucianomartins.transactions.models.CustomerTransaction
import com.lucianomartins.transactions.models.TransactionType
import com.lucianomartins.transactions.repositories.AccountRepository
import com.lucianomartins.transactions.repositories.CustomerTransactionRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@ExtendWith(SpringExtension::class)
class MakeTransferTest {

    @Test
    fun `given an account has sufficient balance, a transfer can be made`() {
        //arrange
        val originAccount = Mock.createAccountWithMoreThan1000L()
        val destinationAccount = Mock.createAccountWithLessThan1000L()
        val originAccountBalance = originAccount.balance
        val destinationAccountBalance = destinationAccount.balance

        MockRepository.accountSaveReturnsMock()
        MockRepository.accountFindByIdOrNullReturnsAccount(originAccount)
        MockRepository.accountFindByIdOrNullReturnsAccount(destinationAccount)
        MockRepository.mockSaveTransaction()

        val executableCase = MakeTransfer(MockRepository.accountRepository, MockRepository.transactionRepository)

        //act
        executableCase(originAccount.id, destinationAccount.id, BigDecimal.valueOf(1000))

        //assert
        Assertions.assertEquals(originAccount.balance, originAccountBalance - BigDecimal.valueOf(1000))
        Assertions.assertEquals(destinationAccount.balance, destinationAccountBalance + BigDecimal.valueOf(1000))
        verify { MockRepository.transactionRepository.save(any()) }
    }

    @Test
    fun `given an account has sufficient balance but the value is greater than 1000, a transfer can not be made`() {
        //arrange
        val originAccount = Mock.createAccountWithMoreThan1000L()
        val destinationAccount = Mock.createAccountWithLessThan1000L()
        val executableCase = MakeTransfer(MockRepository.accountRepository, MockRepository.transactionRepository)
        val valueToTransfer = 1001L

        MockRepository.accountFindByIdOrNullReturnsAccount(originAccount)
        MockRepository.accountFindByIdOrNullReturnsAccount(destinationAccount)
        MockRepository.mockSaveTransaction()

        //act //assert
        assertThrows<java.lang.UnsupportedOperationException> {
            executableCase(originAccount.id, destinationAccount.id, BigDecimal.valueOf(valueToTransfer))
        }
    }

    @Test
    fun `given an account has insufficient balance, a transfer can not be made`() {
        //arrange
        val originAccount = Mock.createAccountWithLessThan1000L()
        val destinationAccount = Mock.createAccountWithLessThan1000L()
        val executableCase = MakeTransfer(MockRepository.accountRepository, MockRepository.transactionRepository)
        val valueToTransfer = 1000L

        MockRepository.accountFindByIdOrNullReturnsAccount(originAccount)
        MockRepository.accountFindByIdOrNullReturnsAccount(destinationAccount)
        MockRepository.mockSaveTransaction()

        //act //assert
        assertThrows<UnsupportedOperationException> {
            executableCase(originAccount.id, destinationAccount.id, BigDecimal.valueOf(valueToTransfer))
        }
    }

    @Test
    fun `given a transfer is made for the same account an exception must be thrown`() {
        //arrange
        val originAccount = Mock.createAccountWithLessThan1000L()
        val executableCase = MakeTransfer(MockRepository.accountRepository, MockRepository.transactionRepository)
        val valueToTransfer = 1000L

        MockRepository.accountFindByIdOrNullReturnsAccount(originAccount)
        MockRepository.mockSaveTransaction()

        //act //assert
        assertThrows<UnsupportedOperationException> {
            executableCase(originAccount.id, originAccount.id, BigDecimal.valueOf(valueToTransfer))
        }
    }
}