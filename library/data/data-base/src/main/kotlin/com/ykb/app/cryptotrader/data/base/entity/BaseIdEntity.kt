package com.ykb.app.cryptotrader.data.base.entity

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseIdEntity : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected val id: Int = 0

    fun getId(): Int {
        return id
    }

}