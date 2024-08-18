package com.ebucelik.paysplit.controller

import com.ebucelik.paysplit.dto.MessageResponseDto
import com.ebucelik.paysplit.dto.RegisterDto
import com.ebucelik.paysplit.service.AccountService
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account")
class AccountController(private val accountService: AccountService) {

    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterDto): ResponseEntity<out Any> {
        return try {
            ResponseEntity.ok(accountService.register(registerDto.toAccount()))
        } catch (e: Exception) {
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                    e.message?.let { message ->
                        MessageResponseDto(message)
                    }
                )
        }
    }

    @GetMapping("/test")
    fun test(): ResponseEntity<out Any> {
        return ResponseEntity.ok(MessageResponseDto("Geht"))
    }
}