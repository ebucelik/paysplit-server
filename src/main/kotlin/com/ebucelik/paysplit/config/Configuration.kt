package com.ebucelik.paysplit.config

import com.ebucelik.paysplit.repository.AccountRepository
import com.ebucelik.paysplit.service.BankDetailService
import com.ebucelik.paysplit.serviceImplementation.AccountServiceImpl
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration {

    @Bean
    fun accountService(
        accountRepository: AccountRepository,
        bankDetailService: BankDetailService
    ): UserDetailsService {
        return AccountServiceImpl(
            accountRepository,
            bankDetailService
        )
    }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(
        accountRepository: AccountRepository,
        bankDetailService: BankDetailService
    ): AuthenticationProvider {
        return DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(
                    accountService(accountRepository, bankDetailService)
                )
                it.setPasswordEncoder(encoder())
            }
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}