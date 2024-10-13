package com.ebucelik.paysplit.controller

import com.ebucelik.paysplit.dto.MessageResponseDto
import com.ebucelik.paysplit.entity.Expense
import com.ebucelik.paysplit.service.ExpenseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/expense")
class ExpenseController(private val expenseService: ExpenseService) {

    @PostMapping("/add")
    fun addExpense(@RequestBody expenses: List<Expense>): ResponseEntity<Any> {
        return try {
            val expensesResponse = expenses.map { expenseService.addExpense(it) }.toList()

            ResponseEntity.ok(expensesResponse)
        } catch (e: Exception) {
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                    e.message?.let { message ->
                        MessageResponseDto(message)
                    }
                )
        }
    }
}