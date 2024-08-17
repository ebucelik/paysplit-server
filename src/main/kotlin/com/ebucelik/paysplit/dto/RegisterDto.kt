package com.ebucelik.paysplit.dto

import com.ebucelik.paysplit.entity.Account
import com.ebucelik.paysplit.entity.BankDetail

class RegisterDto(
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val picturelink: String = "",
    val bankdetail: BankDetail? = null
) {
    fun toAccount(): Account {
        val account = Account()

        account.username = this.username
        account.password = this.password
        account.firstname = this.firstname
        account.lastname = this.lastname
        account.picturelink = this.picturelink
        account.bankdetail = this.bankdetail

        return account
    }
}