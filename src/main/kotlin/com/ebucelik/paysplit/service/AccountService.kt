package com.ebucelik.paysplit.service

import com.ebucelik.paysplit.entity.Account

interface AccountService {
    fun login(username: String, password: String): Account?
    fun register(account: Account): Account
    fun findAccountByUsername(username: String): Account?
    fun findAccountsByUsernameOrFirstnameOrLastname(searchTerm: String): List<Account>
    fun findAccountsByIds(ids: List<Long>): List<Account>

    fun deleteAccountById(id: Long)
}