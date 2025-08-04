package com.ykb.app.cryptotrader.data.domain.dao

import com.ykb.app.cryptotrader.data.base.Dao
import com.ykb.app.cryptotrader.data.domain.model.TradeBot
import com.ykb.app.cryptotrader.data.domain.repo.TradeBotRepo
import com.ykb.app.cryptotrader.utils.enums.TradeBotStatus
import org.springframework.stereotype.Component

@Component
class TradeBotDao(repo: TradeBotRepo) : Dao<TradeBot, Int>(repo) {

    fun findByStatus(status: TradeBotStatus): List<TradeBot> {
        return (repo as TradeBotRepo).findByStatus(status)
    }

}