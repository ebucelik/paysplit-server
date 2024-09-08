package com.ebucelik.paysplit.dto

import com.ebucelik.paysplit.entity.Account

data class AuthenticationResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val account: Account
)
