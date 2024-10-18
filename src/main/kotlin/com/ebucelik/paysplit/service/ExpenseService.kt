package com.ebucelik.paysplit.service

import com.ebucelik.paysplit.entity.Expense
import com.ebucelik.paysplit.entity.ExpenseDetail

interface ExpenseService {
    fun addExpense(expense: Expense): Expense
    fun getExpenseDetails(creatorId: Long, expenseDescription: String, timestamp: Double): List<ExpenseDetail>
    fun getGroupedExpensesByCreatorId(creatorId: Long): List<Expense>
}