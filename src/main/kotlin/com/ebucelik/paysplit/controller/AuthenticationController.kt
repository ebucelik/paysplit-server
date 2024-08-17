package com.ebucelik.paysplit.controller

import com.ebucelik.paysplit.dto.ErrorMessageDto
import com.ebucelik.paysplit.dto.LoginDto
import com.ebucelik.paysplit.dto.RegisterDto
import com.ebucelik.paysplit.exception.UsernameOrPasswordWrongException
import com.ebucelik.paysplit.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(private val accountService: AccountService) {

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<out Any> {
        try {
            val account = accountService.login(loginDto.username, loginDto.password)

            return ResponseEntity.ok(account)
        } catch (e: UsernameOrPasswordWrongException) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                    e.message?.let { message ->
                        ErrorMessageDto(message)
                    }
                )
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterDto): ResponseEntity<out Any> {
        return try {
            ResponseEntity.ok(accountService.register(registerDto.toAccount()))
        } catch (e: Exception) {
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                    e.message?.let { message ->
                        ErrorMessageDto(message)
                    }
                )
        }
    }
}