package com.lucianomartins.transactions.controllers.v1

import com.lucianomartins.transactions.dto.CustomerTransactionResponse
import com.lucianomartins.transactions.usecase.FindTransactionsByAccount
import io.swagger.annotations.Api
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customer-transactions")
@Api(value = "customer transactions")
class CustomerTransactionController(val findTransactionsByAccount: FindTransactionsByAccount) {

    @ApiResponses( value = [
        ApiResponse(code = 200, message = "returns the list of all transactions"),
        ApiResponse(code = 404, message = "nor even a transaction was found")
    ])
    @GetMapping
    fun getTransactionByAccount(
        @ApiParam(value = "The account id to retrieve the transactions from")
        @RequestParam accountId: String): ResponseEntity<List<CustomerTransactionResponse>> {
        return ResponseEntity.ok(findTransactionsByAccount(accountId))
    }
}