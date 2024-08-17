package com.ebucelik.paysplit.controller

import com.ebucelik.paysplit.dto.*
import com.ebucelik.paysplit.exception.UsernameOrPasswordWrongException
import com.ebucelik.paysplit.serviceImplementation.AuthenticationServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
    private val authenticationServiceImpl: AuthenticationServiceImpl
) {

    @PostMapping("/login")
    fun login(@RequestBody authenticationRequestDto: AuthenticationRequestDto): ResponseEntity<out Any> {
        try {
            val authenticationResponseDto = authenticationServiceImpl.authentication(authenticationRequestDto)

            return ResponseEntity.ok(authenticationResponseDto)
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

    @PostMapping("/logout")
    fun logout(): ResponseEntity<out Any> {
        return ResponseEntity.ok("logged out.")
    }

    @PostMapping("/refresh")
    fun refreshAccessToken(
        @RequestBody refreshTokenRequestDto: RefreshTokenRequestDto
    ): ResponseEntity<out Any> {
        try {
            val accessToken = authenticationServiceImpl.refreshAccessToken(refreshTokenRequestDto.token)

            if (accessToken != null) {
                return ResponseEntity.ok(AccessTokenResponseDto(accessToken))
            } else {
                throw UsernameOrPasswordWrongException("Please log in again.")
            }
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
}