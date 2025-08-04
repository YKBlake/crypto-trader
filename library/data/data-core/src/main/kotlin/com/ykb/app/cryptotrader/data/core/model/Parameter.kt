package com.ykb.app.cryptotrader.data.core.model

import com.ykb.app.cryptotrader.data.base.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "PARAMETER")
class Parameter(
    @Id
    @Column(columnDefinition = "VARCHAR(128)")
    private val name: String,
    @Column(columnDefinition = "VARCHAR(512)")
    private val value: String,
) : BaseEntity() {

    fun getValue(): String = value
    fun getName(): String = name

}