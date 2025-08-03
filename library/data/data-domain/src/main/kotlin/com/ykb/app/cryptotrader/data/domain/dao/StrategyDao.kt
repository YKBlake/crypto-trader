package com.ykb.app.cryptotrader.data.domain.dao

import com.ykb.app.cryptotrader.data.base.Dao
import com.ykb.app.cryptotrader.data.domain.model.Strategy
import com.ykb.app.cryptotrader.data.domain.model.StrategySettings
import com.ykb.app.cryptotrader.data.domain.repo.StrategyRepo
import com.ykb.app.cryptotrader.utils.enums.StrategyNames
import org.springframework.stereotype.Component

@Component
class StrategyDao(repo: StrategyRepo) : Dao<Strategy, StrategyNames>(repo) {

    fun findByParams(name: StrategyNames, settings: StrategySettings): Strategy? {
        val list = (repo as StrategyRepo).findByParams(name, settings)
        return list.stream()
            .findFirst()
            .orElse(null)
    }

}