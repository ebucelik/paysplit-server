package com.ebucelik.paysplit.service

import com.ebucelik.paysplit.entity.Expense

interface ExpenseService {
    fun addExpense(expense: Expense): Expense
}