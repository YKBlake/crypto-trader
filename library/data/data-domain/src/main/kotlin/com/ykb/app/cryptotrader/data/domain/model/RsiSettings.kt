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
@Table(name = "RSI_SETTINGS")
class RsiSettings(
    length: Int,
    source: AssetSourceType,
    smoothingMaLength: Int
) : StrategySettings() {

    companion object {
        const val LENGTH_PARAM = "length"
        const val SOURCE_PARAM = "source"
        const val SMOOTHING_MA_LENGTH_PARAM = "smoothingMaLength"
    }

    @EmbeddedId
    private val key = Key(
        length,
        source,
        smoothingMaLength
    )

    @Embeddable
    data class Key(
        @Column("LENGTH", columnDefinition = "INT")
        val length: Int,

        @Column(name = "SOURCE", columnDefinition = "VARCHAR(32)")
        @Enumerated(EnumType.STRING)
        val source: AssetSourceType,

        @Column("SMOOTHING_MA_LENGTH", columnDefinition = "INT")
        val smoothingMaLength: Int,
    )

    fun getLength(): Int {
        return key.length
    }

    fun getSource(): AssetSourceType {
        return key.source
    }

    fun getSmoothingMaLength(): Int {
        return key.smoothingMaLength
    }

}