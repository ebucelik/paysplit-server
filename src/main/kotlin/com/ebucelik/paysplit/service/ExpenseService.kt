package com.ebucelik.paysplit.service

import com.ebucelik.paysplit.entity.Expense

interface ExpenseService {
    fun addExpense(expense: Expense): Expense
    fun getExpensesByCreatorId(creatorId: Long): List<Expense>
    fun getGroupedExpensesByCreatorId(creatorId: Long): List<Expense>
}