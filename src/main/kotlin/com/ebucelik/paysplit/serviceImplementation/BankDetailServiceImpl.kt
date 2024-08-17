package com.ebucelik.paysplit.serviceImplementation

import com.ebucelik.paysplit.entity.BankDetail
import com.ebucelik.paysplit.repository.BankDetailRepository
import com.ebucelik.paysplit.service.BankDetailService
import org.springframework.stereotype.Service

@Service
class BankDetailServiceImpl(private val bankDetailRepository: BankDetailRepository) : BankDetailService {
    override fun findByIban(iban: String): BankDetail? {
        return bankDetailRepository.findByIban(iban)
    }
}