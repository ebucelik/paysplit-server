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

    override fun getExpensesByCreatorId(creatorId: Long): List<Expense> {
        val expenses = expenseRepository.findExpensesByCreatorId(creatorId)

        val expensesSummedUp = expenses.stream().map { expense ->
            val sumAmount = expenses
                .filter {
                    it.creatorId == expense.creatorId &&
                            it.expenseDescription == expense.expenseDescription &&
                            it.timestamp == expense.timestamp
            }.sumOf { it.expenseAmount.trim().replace(",", ".").toDouble() }

            val newExpense = Expense()
            newExpense.id = expense.id
            newExpense.creatorId = expense.creatorId
            newExpense.debtorId = expense.debtorId
            newExpense.expenseDescription = expense.expenseDescription
            newExpense.expenseAmount = sumAmount.toString().replace(".", ",")
            newExpense.paid = expense.paid
            newExpense.timestamp = expense.timestamp

            return@map newExpense
        }
            .toList()
            .groupBy { Triple(it.creatorId, it.expenseDescription, it.timestamp) }
            .map { it.value.first() }

        return expensesSummedUp
    }
}