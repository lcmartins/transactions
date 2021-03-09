package com.lucianomartins.transactions.usecase

import com.lucianomartins.transactions.mocks.Mock
import com.lucianomartins.transactions.mocks.MockRepository
import com.lucianomartins.transactions.repositories.AccountRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.NoSuchElementException

@ExtendWith(SpringExtension::class)
class FindCustomerByAccountTest {

    @Test
    fun `given an account exists a customer must exits too`() {
        //arrange
        MockRepository.accountFindByIdReturnsMock()
        val executable = FindCustomerByAccount(MockRepository.accountRepository)

        //act
        val result = executable("account_1")

        //assert
        Assertions.assertEquals(result.id, Mock.createCustomerMock().id)
        Assertions.assertEquals(result.name, Mock.createCustomerMock().name)
    }

    @Test
    fun `given an account does not exits when searching by account, an exception must be thrown`() {
        //arrange
        MockRepository.accountFindByIdReturnsNull()

        //act //assert
        assertThrows<NoSuchElementException> {
            FindCustomerByAccount(MockRepository.accountRepository)("account_1")
        }
    }
}