package com.ebucelik.paysplit.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(unique = true)
    var username: String = ""

    @Column
    var password: String = ""
        @JsonIgnore
        get() = field

        set(value) {
            field = BCryptPasswordEncoder().encode(value)
        }

    @Column
    var firstname: String = ""

    @Column
    var lastname: String = ""

    @Column
    var picturelink: String = ""

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "bankdetail", referencedColumnName = "id")
    var bankdetail: BankDetail? = null

    fun comparePassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }
}