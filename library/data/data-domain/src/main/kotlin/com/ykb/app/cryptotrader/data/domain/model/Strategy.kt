package com.ykb.app.cryptotrader.data.domain.model

import com.ykb.app.cryptotrader.domain.strategy.StrategyNames
import com.ykb.app.cryptotrader.domain.strategy.StrategySettings
import com.ykb.app.cryptotrader.domain.strategy.settings.MacdSettings
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "STRATEGY")
class Strategy(
    @Id
    @Column("NAME", columnDefinition = "VARCHAR(256)")
    @Enumerated(EnumType.STRING)
    val name: StrategyNames,
    @Transient
    private var settings: StrategySettings
) {


    @Column(name = "SETTINGS", columnDefinition = "JSON", nullable = false)
    private var settingsString = settings.toJsonString()

    fun setSettings(settings: StrategySettings) {
        this.settings = settings
        this.settingsString = settings.toJsonString()
    }

    fun getSettings(): StrategySettings {
        return settings
    }

}