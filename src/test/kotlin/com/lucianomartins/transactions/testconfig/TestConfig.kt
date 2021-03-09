package com.lucianomartins.transactions.testconfig

import com.lucianomartins.transactions.repositories.AccountRepository
import com.lucianomartins.transactions.repositories.CustomerRepository
import com.lucianomartins.transactions.repositories.CustomerTransactionRepository
import io.mockk.mockk
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
class TestConfig {
    @Bean
    fun accountRepository(): AccountRepository = mockk()

    @Bean
    fun customerRepository(): CustomerRepository = mockk()

    @Bean
    fun customerTransactionRepository(): CustomerTransactionRepository = mockk()
}