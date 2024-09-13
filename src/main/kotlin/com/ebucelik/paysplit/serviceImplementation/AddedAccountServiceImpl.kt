package com.ebucelik.paysplit.serviceImplementation

import com.ebucelik.paysplit.dto.MessageResponseDto
import com.ebucelik.paysplit.entity.Account
import com.ebucelik.paysplit.entity.AddedAccount
import com.ebucelik.paysplit.repository.AccountRepository
import com.ebucelik.paysplit.repository.AddedAccountRepository
import com.ebucelik.paysplit.service.AccountService
import com.ebucelik.paysplit.service.AddedAccountService
import org.springframework.stereotype.Service

@Service
class AddedAccountServiceImpl(
    private val addedAccountRepository: AddedAccountRepository,
    private val accountService: AccountService
): AddedAccountService {
    override fun addAccount(firstId: Long, secondId: Long): MessageResponseDto {
        val addedAccount = AddedAccount()
        addedAccount.firstId = firstId
        addedAccount.secondId = secondId

        addedAccountRepository.save(addedAccount)

        return MessageResponseDto("Successfully added!")
    }

    override fun removeAccount(firstId: Long, secondId: Long): MessageResponseDto {
        var savedAddedAccount = addedAccountRepository.findAddedAccountByFirstIdAndSecondId(firstId, secondId)

        if (savedAddedAccount == null) {
            savedAddedAccount = addedAccountRepository.findAddedAccountByFirstIdAndSecondId(secondId, firstId)
        }

        if (savedAddedAccount != null) {
            addedAccountRepository.delete(savedAddedAccount)

            return MessageResponseDto("Successfully removed!")
        }

        throw Exception("Person not found.")
    }

    override fun getAddedAccounts(id: Long): List<Account> {
        val addedAccountIds = addedAccountRepository.findAddedAccountsByFirstIdOrSecondId(id, id)
            .map {
                if (it.firstId == id) {
                    it.secondId
                } else {
                    it.firstId
                }
            }.toList()

        return accountService.findAccountsByIds(addedAccountIds)
    }
}