package com.ebucelik.paysplit.repository

import com.ebucelik.paysplit.entity.AddedAccount
import org.springframework.data.jpa.repository.JpaRepository

interface AddedAccountRepository: JpaRepository<AddedAccount, Long> {
    fun findAddedAccountsByFirstIdOrSecondId(firstId: Long, secondId: Long): List<AddedAccount>

    fun findAddedAccountByFirstIdAndSecondId(firstId: Long, secondId: Long): AddedAccount?
}