package com.ebucelik.paysplit.serviceImplementation

import com.ebucelik.paysplit.entity.Expense
import com.ebucelik.paysplit.repository.ExpenseRepository
import com.ebucelik.paysplit.service.ExpenseService
import org.springframework.stereotype.Service

@Service
class ExpenseServiceImpl(private val expenseRepository: ExpenseRepository): ExpenseService {
    override fun addExpense(expense: Expense): Expense {
        return expenseRepository.save(expense)
    }
}