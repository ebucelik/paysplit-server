package com.ebucelik.paysplit.repository

import com.ebucelik.paysplit.entity.Expense
import com.ebucelik.paysplit.entity.ExpenseDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ExpenseRepository: JpaRepository<Expense, Long> {
    fun findExpensesByCreatorId(creatorId: Long): List<Expense>
}