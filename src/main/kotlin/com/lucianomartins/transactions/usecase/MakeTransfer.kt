package com.lucianomartins.transactions.usecase

import com.lucianomartins.transactions.models.Account
import com.lucianomartins.transactions.models.CustomerTransaction
import com.lucianomartins.transactions.models.TransactionType
import com.lucianomartins.transactions.repositories.AccountRepository
import com.lucianomartins.transactions.repositories.CustomerTransactionRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.UnsupportedOperationException
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional

/**
 * UseCase used to make a transfer between 2 accounts
 * the accounts cannot be the same
 * the accounts must exist
 * the origin account must have sufficient balance
 * the intended transfer amount must be 1000 at most
 * even if a transfer cannot be made a transaction is recorded
 * @property accountRepository
 * @property customerTransactionRepository
 */
@Service
class MakeTransfer(
    val accountRepository: AccountRepository,
    val customerTransactionRepository: CustomerTransactionRepository
) {

    /**
     * process the transaction after validating the UseCase's rule
     *
     * @param originAccountId the origin account id
     * @param destinationAccountId the destination account id
     * @param value the intended value to transfer
     */
    operator fun invoke(originAccountId: String, destinationAccountId: String, value: BigDecimal) {
        if(!processTransaction(originAccountId, destinationAccountId, value)) {
            throw UnsupportedOperationException("operation could not be made, but transaction was recorded")
        }
    }

    @Transactional
    private fun processTransaction(
        originAccountId: String,
        destinationAccountId: String,
        value: BigDecimal
    ): Boolean {
        if (originAccountId === destinationAccountId) {
            return false
        }
        val originAccount = accountRepository.findByIdOrNull(originAccountId)
        val destinationAccount = accountRepository.findByIdOrNull(destinationAccountId)
        if (originAccount == null || destinationAccount == null) {
            return false
        }
        if (valueIsInsideBound(value) && originAccount.canTransfer(value)) {
            transfer(originAccount, destinationAccount, value)
            recordTransaction(originAccount, destinationAccount, value, true)
            return true
        }
        recordTransaction(originAccount, destinationAccount, value, false)
        return false
    }

    private fun recordTransaction(
        originAccount: Account,
        destinationAccount: Account,
        value: BigDecimal,
        status: Boolean
    ) {
        val controlCode = UUID.randomUUID()
        val originTransaction = createTransaction(originAccount, destinationAccount, value, TransactionType.DEBIT, status, controlCode)
        val destinationTransaction = createTransaction(originAccount, destinationAccount, value, type = TransactionType.CREDIT, status, controlCode)
        customerTransactionRepository.save(originTransaction)
        customerTransactionRepository.save(destinationTransaction)
    }

    private fun createTransaction(
        originAccount: Account,
        destinationAccount: Account,
        value: BigDecimal,
        type: TransactionType,
        status: Boolean,
        controlCode: UUID
    ) = CustomerTransaction(
        originAccount = originAccount,
        destinationAccount = destinationAccount,
        transactionValue = value,
        type = type,
        completed = status,
        controlCode = controlCode,
        transactionDate = LocalDateTime.now()
    )

    private fun transfer(
        originAccount: Account,
        destinationAccount: Account,
        value: BigDecimal
        ) {
        originAccount.makeDebit(value)
        destinationAccount.makeCredit(value)
        accountRepository.save(originAccount)
        accountRepository.save(destinationAccount)
    }

    private fun valueIsInsideBound(value: BigDecimal) = value <= BigDecimal.valueOf(1000L)
}