package com.ykb.app.cryptotrader.domain.services

import com.ykb.app.cryptotrader.data.domain.dao.MacdSettingsDao
import com.ykb.app.cryptotrader.data.domain.dao.RsiSettingsDao
import com.ykb.app.cryptotrader.data.domain.dao.StrategyDao
import com.ykb.app.cryptotrader.data.domain.model.MacdSettings
import com.ykb.app.cryptotrader.data.domain.model.RsiSettings
import com.ykb.app.cryptotrader.data.domain.model.Strategy
import com.ykb.app.cryptotrader.data.domain.model.StrategySettings
import com.ykb.app.cryptotrader.utils.enums.AssetSourceType
import com.ykb.app.cryptotrader.utils.enums.StrategyNames
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class StrategyOperator(
    private val strategyDao: StrategyDao,
    private val macdSettingsDao: MacdSettingsDao,
    private val rsiSettingsDao: RsiSettingsDao
) {

    @Transactional
    fun loadStrategy(name: StrategyNames, params: Map<String, Any>): Strategy {
        val strategySettings = loadStrategySettings(name, params)

        var strategy = strategyDao.findByParams(name, strategySettings)
        if(strategy==null) {
            strategy = Strategy(name, strategySettings)
            strategyDao.save(strategy)
        }

        return strategy
    }

    private fun loadStrategySettings(name: StrategyNames, params: Map<String, Any>): StrategySettings {
        return when(name) {
            StrategyNames.RSI -> loadRsiStrategySettings(params)
            StrategyNames.MACD -> loadMacdStrategySettings(params)
        }
    }

    private fun loadRsiStrategySettings(params: Map<String, Any>): StrategySettings {
        val length = params[RsiSettings.LENGTH_PARAM] as Int
        val source = params[RsiSettings.SOURCE_PARAM] as AssetSourceType
        val smoothingMaLength = params[RsiSettings.SMOOTHING_MA_LENGTH_PARAM] as Int

        var rsiSettings = rsiSettingsDao.findByParams(length, source, smoothingMaLength)
        if(rsiSettings==null) {
            rsiSettings = RsiSettings(length, source, smoothingMaLength)
            rsiSettingsDao.save(rsiSettings)
        }

        return rsiSettings
    }

    private fun loadMacdStrategySettings(params: Map<String, Any>): StrategySettings {
        val shortMaLength = params[MacdSettings.SHORT_MA_LENGTH_PARAM] as Int
        val shortMaSource = params[MacdSettings.SHORT_MA_SOURCE_PARAM] as AssetSourceType
        val longMaLength = params[MacdSettings.LONG_MA_LENGTH_PARAM] as Int
        val longMaSource = params[MacdSettings.LONG_MA_SOURCE_PARAM] as AssetSourceType
        val signalLength = params[MacdSettings.SIGNAL_LENGTH_PARAM] as Int

        var macdSettings = macdSettingsDao.findByParams(shortMaLength, shortMaSource, longMaLength, longMaSource, signalLength)
        if(macdSettings==null) {
            macdSettings = MacdSettings(shortMaLength, shortMaSource, longMaLength, longMaSource, signalLength)
            macdSettingsDao.save(macdSettings)
        }

        return macdSettings
    }

}