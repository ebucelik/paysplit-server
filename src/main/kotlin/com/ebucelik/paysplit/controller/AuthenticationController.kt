package com.ebucelik.paysplit.controller

import com.ebucelik.paysplit.dto.*
import com.ebucelik.paysplit.exception.UsernameOrPasswordWrongException
import com.ebucelik.paysplit.serviceImplementation.AuthenticationServiceImpl
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
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
    fun login(
        @RequestBody authenticationRequestDto: AuthenticationRequestDto,
        response: HttpServletResponse
    ): ResponseEntity<out Any> {
        try {
            val authenticationResponseDto = authenticationServiceImpl.authentication(authenticationRequestDto)

            val cookie = Cookie("sid", authenticationResponseDto.accessToken)
            cookie.isHttpOnly = true

            response.addCookie(cookie)

            return ResponseEntity.ok(authenticationResponseDto)
        } catch (e: UsernameOrPasswordWrongException) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                    e.message?.let { message ->
                        MessageResponseDto(message)
                    }
                )
        }
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<out Any> {
        val cookie = Cookie("sid", "")
        cookie.maxAge = 0

        response.addCookie(cookie)

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
    }

    @PostMapping("/refresh")
    fun refreshAccessToken(
        @RequestBody refreshTokenRequestDto: RefreshTokenRequestDto,
        response: HttpServletResponse
    ): ResponseEntity<out Any> {
        try {
            val accessToken = authenticationServiceImpl.refreshAccessToken(refreshTokenRequestDto.token)

            if (accessToken != null) {
                val cookie = Cookie("sid", accessToken)
                cookie.isHttpOnly = true

                response.addCookie(cookie)

                return ResponseEntity.ok(AuthenticationResponseDto(accessToken, refreshTokenRequestDto.token))
            } else {
                throw UsernameOrPasswordWrongException("Please log in again.")
            }
        } catch (e: UsernameOrPasswordWrongException) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                    e.message?.let { message ->
                        MessageResponseDto(message)
                    }
                )
        }
    }
}