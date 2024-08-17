package com.ebucelik.paysplit.service

import com.ebucelik.paysplit.entity.BankDetail

interface BankDetailService {
    fun findByIban(iban: String): BankDetail?
}