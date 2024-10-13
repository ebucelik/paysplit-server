package com.ebucelik.paysplit.repository

import com.ebucelik.paysplit.entity.Expense
import org.springframework.data.jpa.repository.JpaRepository

interface ExpenseRepository: JpaRepository<Expense, Long> {
}