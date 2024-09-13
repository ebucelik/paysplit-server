package com.ebucelik.paysplit.service

import com.ebucelik.paysplit.dto.MessageResponseDto
import com.ebucelik.paysplit.entity.Account

interface AddedAccountService {
    fun addAccount(firstId: Long, secondId: Long): MessageResponseDto
    fun removeAccount(firstId: Long, secondId: Long): MessageResponseDto
    fun getAddedAccounts(id: Long): List<Account>
}