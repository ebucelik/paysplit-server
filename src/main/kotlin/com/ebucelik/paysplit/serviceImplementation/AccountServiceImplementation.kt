package com.ebucelik.paysplit.serviceImplementation

import com.ebucelik.paysplit.entity.Account
import com.ebucelik.paysplit.exception.UsernameOrPasswordWrongException
import com.ebucelik.paysplit.repository.AccountRepository
import com.ebucelik.paysplit.service.AccountService
import com.ebucelik.paysplit.service.BankDetailService
import org.springframework.stereotype.Service

@Service
class AccountServiceImplementation(
    private val accountRepository: AccountRepository,
    private val bankDetailService: BankDetailService
) : AccountService {

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

        if (account.bankdetail != null) {
            val foundBankDetail = account.bankdetail.let { bankDetail -> bankDetail?.let { bankDetailService.findByIban(it.iban) }}

            if (foundBankDetail != null) {
                throw Exception("Bank informations already exists.")
            }
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