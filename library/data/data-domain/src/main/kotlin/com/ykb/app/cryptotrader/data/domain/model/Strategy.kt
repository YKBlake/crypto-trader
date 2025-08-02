package com.ykb.app.cryptotrader.data.domain.model

import com.ykb.app.cryptotrader.data.base.entity.BaseIdEntity
import com.ykb.app.cryptotrader.domain.enums.StrategyNames
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "STRATEGY")
class Strategy(
    @Column("NAME", columnDefinition = "VARCHAR(256)")
    @Enumerated(EnumType.STRING)
    val name: StrategyNames,

    @ManyToOne
    @JoinColumn(name = "SETTINGS_ID", nullable = false)
    private val settings: StrategySettings
) : BaseIdEntity()