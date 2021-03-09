package com.lucianomartins.transactions.controllers.v1

import com.lucianomartins.transactions.dto.AccountCustomerPostRequest
import com.lucianomartins.transactions.dto.AccountCustomerPostResponse
import com.lucianomartins.transactions.dto.TransferPutRequest
import com.lucianomartins.transactions.usecase.MakeTransfer
import com.lucianomartins.transactions.usecase.OpenAccount
import io.swagger.annotations.Api
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("account")
@Api(value = "account api")
class AccountController(val openAccount: OpenAccount, val makeTransfer: MakeTransfer) {
    @ApiResponses( value = [
        ApiResponse(code = 203, message = "account created"),
        ApiResponse(code = 400, message = "bad request")
    ])
    @PostMapping
    fun create(
        @ApiParam(value = "The object used to create the account")
        @Valid
        @RequestBody accountCustomer: AccountCustomerPostRequest): ResponseEntity<AccountCustomerPostResponse> {
        return ResponseEntity(openAccount(accountCustomer), HttpStatus.CREATED)
    }

    @ApiResponses( value = [
        ApiResponse(code = 200, message = "transfer completed"),
        ApiResponse(code = 400, message = "bad request, check the accounts, intended transfer value or balances")
    ])
    @PutMapping
    fun makeTransfer(
        @ApiParam(value = "the transfer object")
        @Valid
        @RequestBody transferPutRequest: TransferPutRequest): ResponseEntity<Unit> {
        makeTransfer(transferPutRequest.originAccountId, transferPutRequest.destinationAccountId, transferPutRequest.intendedValue)
        return ResponseEntity.ok().build()
    }
}