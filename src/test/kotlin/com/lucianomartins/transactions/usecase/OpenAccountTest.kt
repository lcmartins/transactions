package com.lucianomartins.transactions.usecase

import com.lucianomartins.transactions.dto.AccountCustomerPostRequest
import com.lucianomartins.transactions.mocks.Mock
import com.lucianomartins.transactions.repositories.AccountRepository
import com.lucianomartins.transactions.repositories.CustomerRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.lang.UnsupportedOperationException
import java.math.BigDecimal

@ExtendWith(SpringExtension::class)
class OpenAccountTest {
    private val accountRepo = mockk<AccountRepository>()
    private val customerRepo = mockk<CustomerRepository>()

    @Test
    fun `given the passed account and customer does not exists, an account and a customer must be created`() {
        //arrange
        accountFindByIdReturnsEmpty()
        accountSaveCreatesObject()
        findCustomerByIdReturnsEmpty()

        every {
            customerRepo.save(any())
        } returns Mock.createCustomerMock()

        val businessObject = mockAccountCustomerPostBody()
        val runUseCase = OpenAccount(customerRepo, accountRepo)

        //act
        val result = runUseCase(businessObject)

        //assert
        Assertions.assertEquals(result.customerId, businessObject.customerId)
        Assertions.assertEquals(result.customerName, businessObject.customerName)
        Assertions.assertEquals(result.accountId, businessObject.accountId)
        Assertions.assertEquals(result.accountBalance, businessObject.accountBalance)

    }

    private fun findCustomerByIdReturnsEmpty() {
        every {
            customerRepo.findByIdOrNull(any())
        } returns null
    }

    @Test
    fun `given the account does not exists but customer, a new account attached to that customer must be created`() {
        //arrange
        accountFindByIdReturnsEmpty()
        accountSaveCreatesObject()
        every {
            customerRepo.findByIdOrNull(any())
        } returns Mock.createCustomerMock()

        val businessObject = mockAccountCustomerPostBody()
        val runUseCase = OpenAccount(customerRepo, accountRepo)

        //act
        val result = runUseCase(businessObject)

        //assert
        Assertions.assertEquals(result.customerId, businessObject.customerId)
        Assertions.assertEquals(result.customerName, businessObject.customerName)
        Assertions.assertEquals(result.accountId, businessObject.accountId)
        Assertions.assertEquals(result.accountBalance, businessObject.accountBalance)

    }

    @Test
    fun `given an account that exists is sent to be created and exception should be thrown`() {
        //arrange
        findCustomerByIdReturnsEmpty()
        accountFindByIdReturnsMock()
        val runUseCase = OpenAccount(customerRepo, accountRepo)
        val businessObject = mockAccountCustomerPostBody()
        //act //assert
        assertThrows<UnsupportedOperationException> {
            runUseCase(businessObject)
        }
    }

    private fun mockAccountCustomerPostBody(): AccountCustomerPostRequest {
        return AccountCustomerPostRequest(
            "customer_id_1",
            "fulano sales",
            "account_1",
            BigDecimal.valueOf(1000L)
        )
    }

    private fun accountSaveCreatesObject() {
        every {
            accountRepo.save(any())
        } returns Mock.createAccountMock()
    }

    private fun accountFindByIdReturnsEmpty() {
        every {
            accountRepo.findByIdOrNull(any())
        } returns null
    }

    private fun accountFindByIdReturnsMock() {
        every {
            accountRepo.findByIdOrNull(any())
        } returns Mock.createAccountMock()
    }
}