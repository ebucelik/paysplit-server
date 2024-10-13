package com.ebucelik.paysplit.entity

import jakarta.persistence.*

@Entity
class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(nullable = false)
    var creatorId: Long = 0

    @Column(nullable = false)
    var debtorId: Long = 0

    @Column(nullable = false)
    var expenseDescription: String = ""

    @Column(nullable = false)
    var expenseAmount: String = ""

    @Column(nullable = false)
    var paid: Boolean = false

    @Column
    var timestamp: Double = 0.0
}