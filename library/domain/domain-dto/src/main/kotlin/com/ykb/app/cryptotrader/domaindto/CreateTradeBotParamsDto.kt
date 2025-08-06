package com.ykb.app.cryptotrader.domaindto

import com.ykb.app.cryptotrader.domaindto.base.Dto
import com.ykb.app.cryptotrader.utils.enums.AssetSourceType
import com.ykb.app.cryptotrader.utils.enums.StrategyNames

data class CreateTradeBotParamsDto(
    val strategyName: StrategyNames,
    val rsiSettings: RsiSettings?,
    val macdSettings: MacdSettings?
) : Dto {

    data class MacdSettings(
        val shortMaLength: Int?,
        val shortMaSource: AssetSourceType?,
        val longMaLength: Int?,
        val longMaSource: AssetSourceType?,
        val signalLength: Int?
    ) {
        fun toMap(): Map<String, Any> {
            val params = HashMap<String, Any>()
            if(shortMaLength!=null)
                params[com.ykb.app.cryptotrader.data.domain.model.MacdSettings.SHORT_MA_LENGTH_PARAM] = shortMaLength
            if(shortMaSource!=null)
                params[com.ykb.app.cryptotrader.data.domain.model.MacdSettings.SHORT_MA_SOURCE_PARAM] = shortMaSource
            if(longMaLength!=null)
                params[com.ykb.app.cryptotrader.data.domain.model.MacdSettings.LONG_MA_LENGTH_PARAM] = longMaLength
            if(longMaSource!=null)
                params[com.ykb.app.cryptotrader.data.domain.model.MacdSettings.LONG_MA_SOURCE_PARAM] = longMaSource
            if(signalLength!=null)
                params[com.ykb.app.cryptotrader.data.domain.model.MacdSettings.SIGNAL_LENGTH_PARAM] = signalLength
            return params
        }
    }

    data class RsiSettings(
        val length: Int?,
        val source: AssetSourceType?,
        val smoothingMaLength: Int?,
    ) {
        fun toMap(): Map<String, Any> {
            val params = HashMap<String, Any>()
            if(length!=null)
                params[com.ykb.app.cryptotrader.data.domain.model.RsiSettings.LENGTH_PARAM] = length
            if(source!=null)
                params[com.ykb.app.cryptotrader.data.domain.model.RsiSettings.SOURCE_PARAM] = source
            if(smoothingMaLength!=null)
                params[com.ykb.app.cryptotrader.data.domain.model.RsiSettings.SMOOTHING_MA_LENGTH_PARAM] = smoothingMaLength
            return params
        }
    }

}