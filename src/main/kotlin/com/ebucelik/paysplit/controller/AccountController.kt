package com.ebucelik.paysplit.controller

import com.ebucelik.paysplit.dto.MessageResponseDto
import com.ebucelik.paysplit.dto.RegisterDto
import com.ebucelik.paysplit.service.AccountService
import com.ebucelik.paysplit.service.AddedAccountService
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account")
class AccountController(
    private val accountService: AccountService,
    private val addedAccountService: AddedAccountService
) {

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

    @GetMapping("/search")
    fun search(@RequestParam id: Long, @RequestParam term: String): ResponseEntity<out Any> {
        return try {
            val accounts = accountService.findAccountsByUsernameOrFirstnameOrLastname(term).filter { it.id != id }

            return ResponseEntity.ok(accounts)
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

    @PostMapping("/add")
    fun addAccount(@RequestParam firstId: Long, @RequestParam secondId: Long): ResponseEntity<out Any> {
        return try {
            return ResponseEntity.ok(addedAccountService.addAccount(firstId, secondId))
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

    @PostMapping("/remove")
    fun removeAccount(@RequestParam firstId: Long, @RequestParam secondId: Long): ResponseEntity<out Any> {
        return try {
            return ResponseEntity.ok(addedAccountService.removeAccount(firstId, secondId))
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

    @GetMapping("/addedAccounts")
    fun addedAccounts(@RequestParam id: Long): ResponseEntity<out Any> {
        return try {
            return ResponseEntity.ok(addedAccountService.getAddedAccounts(id))
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
}