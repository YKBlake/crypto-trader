package com.ykb.app.cryptotrader.data.domain.dao

import com.ykb.app.cryptotrader.data.base.Dao
import com.ykb.app.cryptotrader.data.domain.model.Strategy
import com.ykb.app.cryptotrader.data.domain.repo.StrategyRepo
import com.ykb.app.cryptotrader.domain.strategy.StrategyNames
import org.springframework.stereotype.Service

@Service
class StrategyDao(repo: StrategyRepo) : Dao<Strategy, StrategyNames>(repo)