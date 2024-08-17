package com.ebucelik.paysplit.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class BankDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(unique = true)
    var iban: String = ""

    @Column
    var bic: String? = null

    @Column
    var firstname: String = ""

    @Column
    var lastname: String = ""

    @JsonIgnore
    @OneToOne(mappedBy = "bankdetail")
    var account: Account? = null
}
