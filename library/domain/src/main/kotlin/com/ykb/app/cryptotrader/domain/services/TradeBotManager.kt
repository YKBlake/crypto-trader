package com.ykb.app.cryptotrader.domain.services

import com.ykb.app.cryptotrader.data.auth.model.User
import com.ykb.app.cryptotrader.data.domain.dao.TradeBotDao
import com.ykb.app.cryptotrader.data.domain.model.TradeBot
import com.ykb.app.cryptotrader.domain.model.TradeBotTask
import com.ykb.app.cryptotrader.domain.model.WrappedTradeBot
import com.ykb.app.cryptotrader.utils.enums.StrategyNames
import com.ykb.app.cryptotrader.utils.enums.TradeBotStatus
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class TradeBotManager(
    private val strategyOperator: StrategyOperator,
    private val tradeBotDao: TradeBotDao
) {

    private val tradeBots = loadAllTradeBotThreads()

    fun doTradeBotExist(id: Int): Boolean {
        return tradeBots.stream()
            .anyMatch { tradeBot -> tradeBot.id == id }
    }

    fun create(user: User, strategyName: StrategyNames, strategyParams: Map<String, Any>): TradeBot {
        val strategy = strategyOperator.loadStrategy(strategyName, strategyParams)
        val entity = tradeBotDao.save(TradeBot(strategy, user))
        initTradeBotThread(entity)
        return entity
    }

    fun terminate(id: Int) {
        val wrappedTradeBot = tradeBots.stream()
            .filter { it -> it.id == id }
            .findFirst()
            .orElseThrow { IllegalArgumentException("There is no tradeBot with id: $id") }

        terminateTradeBotThread(wrappedTradeBot)
    }

    private fun initTradeBotThread(tradeBot: TradeBot) {
        val task = TradeBotTask(tradeBotDao, tradeBot)
        tradeBots.add(
            WrappedTradeBot(
                tradeBot.getId(),
                task,
                Thread.ofVirtual().start(task)
            )
        )
    }

    private fun terminateTradeBotThread(wrappedTradeBot: WrappedTradeBot) {
        wrappedTradeBot.task.terminate()
        val entity = wrappedTradeBot.task.tradeBot
        tradeBotDao.save(entity)
        tradeBots.remove(wrappedTradeBot)
    }

    private fun loadAllTradeBotThreads(): MutableList<WrappedTradeBot> {
        return tradeBotDao.findByStatus(TradeBotStatus.ACTIVE).stream()
            .map {
                val task = TradeBotTask(tradeBotDao, it)
                WrappedTradeBot(
                    it.getId(),
                    task,
                    Thread.ofVirtual().start(task)
                )
            }
            .collect(Collectors.toList())
    }

}