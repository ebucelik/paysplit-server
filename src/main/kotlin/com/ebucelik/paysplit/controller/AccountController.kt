package com.ebucelik.paysplit.controller

import com.ebucelik.paysplit.dto.ErrorMessageDto
import com.ebucelik.paysplit.dto.LoginDto
import com.ebucelik.paysplit.entity.Account
import com.ebucelik.paysplit.exception.UsernameOrPasswordWrongException
import com.ebucelik.paysplit.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account")
class AccountController(private val accountService: AccountService) {


}