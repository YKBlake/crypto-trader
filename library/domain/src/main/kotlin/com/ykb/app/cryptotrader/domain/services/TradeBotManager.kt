package com.ykb.app.cryptotrader.domain.services

import com.ykb.app.cryptotrader.data.auth.model.User
import com.ykb.app.cryptotrader.data.domain.dao.TradeBotDao
import com.ykb.app.cryptotrader.data.domain.model.TradeBot
import com.ykb.app.cryptotrader.utils.enums.StrategyNames
import org.springframework.stereotype.Service

@Service
class TradeBotManager(
    private val strategyOperator: StrategyOperator,
    private val tradeBotDao: TradeBotDao
) {

    fun doTradeBotExist(id: Int): Boolean {
        return tradeBotDao.existsById(id)
    }

    fun create(user: User, strategyName: StrategyNames, strategyParams: Map<String, Any>): TradeBot {
        val strategy = strategyOperator.loadStrategy(strategyName, strategyParams)
        val entity = TradeBot(strategy, user)
        return tradeBotDao.save(entity)
    }

    fun delete(id: Int) {
        val tradeBot = tradeBotDao.findById(id)
        if(tradeBot.isEmpty)
            throw IllegalArgumentException("There is no tradeBot with id: $id")

        val entity = tradeBot.get()
        entity.inactivate()
        tradeBotDao.save(entity)
    }

}