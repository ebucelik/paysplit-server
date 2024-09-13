package com.ebucelik.paysplit.entity

import jakarta.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["firstId", "secondId"])])
class AddedAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(nullable = false)
    var firstId: Long = 0

    @Column(nullable = false)
    var secondId: Long = 0
}