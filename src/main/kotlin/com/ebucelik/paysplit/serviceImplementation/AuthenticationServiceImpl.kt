package com.ebucelik.paysplit.serviceImplementation

import com.ebucelik.paysplit.config.JwtProperties
import com.ebucelik.paysplit.dto.AuthenticationRequestDto
import com.ebucelik.paysplit.dto.AuthenticationResponseDto
import com.ebucelik.paysplit.service.AccountService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationServiceImpl(
    private val authenticationManager: AuthenticationManager,
    private val accountService: AccountService,
    private val jwtService: JwtServiceImpl,
    private val jwtProperties: JwtProperties
) {
    fun authentication(authenticationRequestDto: AuthenticationRequestDto): AuthenticationResponseDto {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequestDto.username,
                authenticationRequestDto.password
            )
        )

        val account = accountService.findAccountByUsername(authenticationRequestDto.username)

        if (account != null) {
            val accessToken = jwtService.generate(
                account,
                getAccessTokenExpiration()
            )

            val refreshToken = jwtService.generate(
                account,
                getRefreshTokenExpiration()
            )

            return AuthenticationResponseDto(
                accessToken,
                refreshToken
            )
        }

        throw Exception("Account not authenticated.")
    }

    fun refreshAccessToken(refreshToken: String): String? {
        val extractedUsername = jwtService.extractUsername(refreshToken)

        return extractedUsername?.let { username ->
            val currentAccount = accountService.findAccountByUsername(username)

            if (currentAccount != null && !jwtService.isExpired(refreshToken)) {
                jwtService.generate(currentAccount, getAccessTokenExpiration())
            } else {
                null
            }
        }
    }

    private fun getAccessTokenExpiration(): Date = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
    private fun getRefreshTokenExpiration(): Date = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
}