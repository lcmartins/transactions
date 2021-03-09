package com.lucianomartins.transactions.mocks

import com.lucianomartins.transactions.models.Account
import com.lucianomartins.transactions.models.Customer
import com.lucianomartins.transactions.models.CustomerTransaction
import com.lucianomartins.transactions.models.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class Mock {
    companion object {
        fun createCustomerMock(): Customer = Customer("customer_id_1", "fulano sales")
        fun createOtherCustomerMock(): Customer = Customer("customer_id_2", "beltrano neves")
        fun createAccountMock(): Account = Account("account_1", createCustomerMock(), BigDecimal.valueOf(1000L))
        fun createAccountWithMoreThan1000L(): Account = Account("account_2", createOtherCustomerMock(), BigDecimal(1550.75) )
        fun createAccountWithLessThan1000L(): Account = Account("account_3", createOtherCustomerMock(), BigDecimal(999.99) )
        fun createTransactionMock(
            originAccount: Account,
            destinationAccount: Account
        ) = CustomerTransaction(
            originAccount = originAccount,
            destinationAccount = destinationAccount,
            transactionValue = BigDecimal.valueOf(1000),
            type = TransactionType.CREDIT,
            completed = true,
            controlCode = UUID.randomUUID(),
            transactionDate = LocalDateTime.now()
        )
    }
}