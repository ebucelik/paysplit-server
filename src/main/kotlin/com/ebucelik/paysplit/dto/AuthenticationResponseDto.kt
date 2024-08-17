package com.ebucelik.paysplit.dto

data class AuthenticationResponseDto(
    val accessToken: String,
    var refreshToken: String
)
