package com.ebucelik.paysplit.service

import com.ebucelik.paysplit.entity.Account

interface AccountService {
    fun findAccountsByUsernameOrFirstnameOrLastname(searchTerm: String): List<Account>

    fun deleteAccountById(id: Long)
}