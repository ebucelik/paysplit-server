package com.ebucelik.paysplit.repository

import com.ebucelik.paysplit.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<Account, Long> {
    fun findAccountsByUsernameOrFirstnameOrLastname(username: String, firstName: String, lastName: String): List<Account>

    fun deleteAccountById(id: Long)
}