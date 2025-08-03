package com.ykb.app.cryptotrader.data.domain.model

import com.ykb.app.cryptotrader.utils.enums.AssetSourceType
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "MACD_SETTINGS")
class MacdSettings(
    shortMaLength: Int,
    shortMaSource: AssetSourceType,
    longMaLength: Int,
    longMaSource: AssetSourceType,
    signalLength: Int
) : StrategySettings() {

    companion object {
        const val SHORT_MA_LENGTH_PARAM = "shortMaLength"
        const val SHORT_MA_SOURCE_PARAM = "shortMaSource"
        const val LONG_MA_LENGTH_PARAM = "longMaLength"
        const val LONG_MA_SOURCE_PARAM = "longMaSource"
        const val SIGNAL_LENGTH_PARAM = "signalLength"
    }

    @EmbeddedId
    private val key = Key(
        shortMaLength,
        shortMaSource,
        longMaLength,
        longMaSource,
        signalLength
    )

    @Embeddable
    data class Key(
        @Column(name = "SHORT_MA_LENGTH", columnDefinition = "INT")
        val shortMaLength: Int,

        @Column(name = "SHORT_MA_SOURCE", columnDefinition = "VARCHAR(32)")
        @Enumerated(EnumType.STRING)
        val shortMaSource: AssetSourceType,

        @Column(name = "LONG_MA_LENGTH", columnDefinition = "INT")
        val longMaLength: Int,

        @Column(name = "LONG_MA_SOURCE", columnDefinition = "VARCHAR(32)")
        @Enumerated(EnumType.STRING)
        val longMaSource: AssetSourceType,

        @Column(name = "SIGNAL_LENGTH", columnDefinition = "INT")
        val signalLength: Int
    )

    fun getShortMaLength(): Int {
        return key.shortMaLength
    }

    fun getShortMaSource(): AssetSourceType {
        return key.shortMaSource
    }

    fun getLongMaLength(): Int {
        return key.longMaLength
    }

    fun getLongMaSource(): AssetSourceType {
        return key.longMaSource
    }

    fun getSignalLength(): Int {
        return key.signalLength
    }

}