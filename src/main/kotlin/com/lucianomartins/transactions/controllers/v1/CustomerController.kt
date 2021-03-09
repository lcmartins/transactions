package com.lucianomartins.transactions.controllers.v1

import com.lucianomartins.transactions.dto.CustomerResponse
import com.lucianomartins.transactions.models.Customer
import com.lucianomartins.transactions.repositories.CustomerRepository
import com.lucianomartins.transactions.usecase.FindAllCustomers
import com.lucianomartins.transactions.usecase.FindCustomerByAccount
import io.swagger.annotations.Api
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customer")
@Api(value = "customer")
class CustomerController(
    val findCustomerByAccount: FindCustomerByAccount,
    val findAllCustomers: FindAllCustomers
) {
    @ApiResponses( value = [
        ApiResponse(code = 200, message = "returns the customer"),
        ApiResponse(code = 404, message = "element not found")
    ])
    @GetMapping("/account/{accountId}")
    fun findByAccount(
        @ApiParam(value = "the account id to retrieve the customer from")
        @PathVariable accountId: String): ResponseEntity<CustomerResponse> =
        ResponseEntity.ok(findCustomerByAccount(accountId))

    @ApiResponses( value = [
        ApiResponse(code = 200, message = "returns the list of all customers"),
        ApiResponse(code = 404, message = "nor even a customer was found")
    ])
    @GetMapping
    fun list(): ResponseEntity<List<CustomerResponse>> =
        ResponseEntity.ok(findAllCustomers())
}