package com.ebucelik.paysplit.repository

import com.ebucelik.paysplit.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AccountRepository: JpaRepository<Account, Long> {

    fun findAccountByUsername(username: String): Account?

    fun findAccountsByUsernameContainingOrFirstnameContainingOrLastnameContaining(username: String, firstName: String, lastName: String): List<Account>

    @Query("select a from Account a WHERE a.id IN (:ids)")
    fun findAccountsBy(@Param("ids") ids: List<Long>): List<Account>

    fun deleteAccountById(id: Long)
}