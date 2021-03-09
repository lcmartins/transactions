package com.lucianomartins.transactions.usecase

import com.lucianomartins.transactions.mocks.Mock
import com.lucianomartins.transactions.mocks.MockRepository
import com.lucianomartins.transactions.repositories.CustomerRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class FindAllCustomersTest {

    @Test
    fun `given at least a customer exists, a list should be returned`() {
        //arrange
        MockRepository.findAllCustomersReturnsMock()
        val executableUsecase = FindAllCustomers(MockRepository.customerRepository)

        //act
        val customers = executableUsecase()

        //assert
        assertThat(customers.isNotEmpty())
    }

    @Test
    fun `given not even a customer exists, an exception should be thrown`() {
        //arrange
        MockRepository.findAllCustomersReturnsEmpty()

        val executableUseCase = FindAllCustomers(MockRepository.customerRepository)

        //act //assert
        assertThrows<NoSuchElementException> {
            executableUseCase()
        }
    }
}