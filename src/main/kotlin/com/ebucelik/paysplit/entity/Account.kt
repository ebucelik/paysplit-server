package com.ebucelik.paysplit.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(unique = true, nullable = false)
    var username: String = ""

    @Column(nullable = false)
    var password: String = ""
        @JsonIgnore
        get() = field

        set(value) {
            field = BCryptPasswordEncoder().encode(value)
        }

    @Column(nullable = false)
    var firstname: String = ""

    @Column(nullable = false)
    var lastname: String = ""

    @Column
    var picturelink: String = ""

    fun comparePassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }
}