package com.ykb.app.cryptotrader.domain.model

import com.ykb.app.cryptotrader.data.domain.dao.TradeBotDao
import com.ykb.app.cryptotrader.data.domain.model.TradeBot

class TradeBotTask(
    private val tradeBotDao: TradeBotDao,
    val tradeBot: TradeBot
) : Runnable {

    override fun run() {
        TODO("Not yet implemented")
    }

    fun terminate() {
        tradeBot.terminate()
    }

    private fun isTerminated(): Boolean {
        return tradeBot.isTerminated()
    }

}