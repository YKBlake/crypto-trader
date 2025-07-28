package com.ykb.app.cryptotrader.data.domain.dao

import com.ykb.app.cryptotrader.data.base.Dao
import com.ykb.app.cryptotrader.data.domain.model.TradeBot
import com.ykb.app.cryptotrader.data.domain.repo.TradeBotRepo
import org.springframework.stereotype.Service

@Service
class TradeBotDao(repo: TradeBotRepo) : Dao<TradeBot, Long>(repo)