package com.ebucelik.paysplit.serviceImplementation

import com.ebucelik.paysplit.config.JwtProperties
import com.ebucelik.paysplit.entity.Account
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.exp

@Service
class JwtServiceImpl(jwtProperties: JwtProperties) {
    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    fun generate(
        account: Account,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String {
        return Jwts
            .builder()
            .setClaims(additionalClaims)
            .setSubject(account.username)
            .setIssuer(account.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(expirationDate)
            .signWith(secretKey)
            .compact()
    }

    fun isValid(token: String, account: Account): Boolean {
        val username = extractUsername(token)

        return account.username == username && !isExpired(token)
    }

    fun extractUsername(token: String): String? {
        return getAllClaims(token)
            .subject
    }

    fun isExpired(token: String): Boolean {
        return getAllClaims(token)
            .expiration
            .before(Date(System.currentTimeMillis()))
    }

    fun getAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }
}