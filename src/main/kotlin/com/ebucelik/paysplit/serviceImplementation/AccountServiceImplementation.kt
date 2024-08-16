package com.ebucelik.paysplit.serviceImplementation

import com.ebucelik.paysplit.entity.Account
import com.ebucelik.paysplit.repository.AccountRepository
import com.ebucelik.paysplit.service.AccountService
import org.springframework.stereotype.Service

@Service
class AccountServiceImplementation(private val accountRepository: AccountRepository) : AccountService {
    override fun findAccountsByUsernameOrFirstnameOrLastname(searchTerm: String): List<Account> {
        return accountRepository.findAccountsByUsernameOrFirstnameOrLastname(
            searchTerm,
            searchTerm,
            searchTerm
        )
    }

    override fun deleteAccountById(id: Long) {
        return accountRepository.deleteAccountById(id)
    }
}