package com.ebucelik.paysplit.serviceImplementation

import com.ebucelik.paysplit.entity.Account
import com.ebucelik.paysplit.exception.UsernameOrPasswordWrongException
import com.ebucelik.paysplit.repository.AccountRepository
import com.ebucelik.paysplit.service.AccountService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository
) : AccountService, UserDetailsService {

    override fun login(username: String, password: String): Account? {
        val account = accountRepository.findAccountByUsername(username)

        if (account != null) {
            if (account.comparePassword(password)) {
                return account
            }
        }

        throw UsernameOrPasswordWrongException("Username or password invalid.")
    }

    override fun register(account: Account): Account {
        val foundAccount = accountRepository.findAccountByUsername(account.username)

        if (foundAccount != null) {
            throw Exception("Username already exists.")
        }

        if (
            account.username.isEmpty()
            || account.firstname.isEmpty()
            || account.lastname.isEmpty()
            ) {
            throw Exception("Please fill out all required fields.")
        }

        return accountRepository.save(account)
    }

    override fun findAccountByUsername(username: String): Account? {
        return accountRepository.findAccountByUsername(username)
    }

    override fun findAccountsByUsernameOrFirstnameOrLastname(searchTerm: String): List<Account> {
        return accountRepository.findAccountsByUsernameContainingOrFirstnameContainingOrLastnameContaining(
            searchTerm,
            searchTerm,
            searchTerm
        )
    }

    override fun findAccountsByIds(ids: List<Long>): List<Account> {
        return accountRepository.findAccountsBy(ids)
    }

    override fun deleteAccountById(id: Long) {
        return accountRepository.deleteAccountById(id)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val userDetails = accountRepository.findAccountByUsername(username)?.let { mapToUserDetails(it) }

        if (userDetails != null) {
            return userDetails
        }

        throw UsernameOrPasswordWrongException("Username or password invalid.")
    }

    private fun mapToUserDetails(account: Account): UserDetails {
        return User
            .builder()
            .username(account.username)
            .password(account.password)
            .build()
    }
}