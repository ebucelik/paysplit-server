package com.ebucelik.paysplit.repository

import com.ebucelik.paysplit.entity.BankDetail
import org.springframework.data.jpa.repository.JpaRepository

interface BankDetailRepository: JpaRepository<BankDetail, Long> {
    fun findByIban(iban: String): BankDetail?
}