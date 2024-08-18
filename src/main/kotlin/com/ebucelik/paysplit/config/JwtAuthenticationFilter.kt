package com.ebucelik.paysplit.config

import com.ebucelik.paysplit.entity.Account
import com.ebucelik.paysplit.service.AccountService
import com.ebucelik.paysplit.serviceImplementation.JwtServiceImpl
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val accountService: AccountService,
    private val jwtService: JwtServiceImpl
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authorizationHeader = request.getHeader("Authorization")

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                val jwtToken = authorizationHeader.substringAfter("Bearer ")
                val username = jwtService.extractUsername(jwtToken)

                if (username != null && SecurityContextHolder.getContext().authentication == null) {
                    val account = accountService.findAccountByUsername(username)

                    if (account != null && jwtService.isValid(jwtToken, account)) {
                        updateSecurityContext(account, request)
                    }

                    filterChain.doFilter(request, response)
                }
            } else {
                filterChain.doFilter(request, response)
                return
            }
        } catch (e: ExpiredJwtException) {
            filterChain.doFilter(request, response)

            println(e.message)
        }
    }

    fun updateSecurityContext(account: Account, request: HttpServletRequest) {
        val authenticationToken = UsernamePasswordAuthenticationToken(account, null, emptyList())
        authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }
}