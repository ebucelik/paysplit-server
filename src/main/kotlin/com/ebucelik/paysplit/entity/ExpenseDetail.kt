package com.ebucelik.paysplit.entity

data class ExpenseDetail(
    private val id: Long,
    private val creatorId: Long,
    private val debtorId: Long,
    private val debtorName: String,
    private val debtorUsername: String,
    private val debtorPictureLink: String,
    private val expenseAmount: String,
    private val paid: Boolean,
    private val timestamp: Double
) {
}