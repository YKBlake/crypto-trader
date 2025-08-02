package com.ykb.app.cryptotrader.data.base.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import java.util.Date

@MappedSuperclass
abstract class BaseEntity {

    @Column(name = "CREATION_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    protected val creationTime = Date()

    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    protected var updateTime: Date? = null

    @Column(name = "INACTIVATION_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    protected var inactivationTime: Date? = null

    fun getCreationTime(): Date {
        return creationTime
    }

    fun getUpdateTime(): Date? {
        return updateTime
    }

    fun update() {
        updateTime = Date()
    }

    fun getInactivationTime(): Date? {
        return inactivationTime
    }

    fun inactivate() {
        updateTime = Date()
        inactivationTime = updateTime
    }

}