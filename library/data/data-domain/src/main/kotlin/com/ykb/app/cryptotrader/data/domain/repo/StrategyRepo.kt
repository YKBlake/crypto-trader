package com.ykb.app.cryptotrader.data.domain.repo

import com.ykb.app.cryptotrader.data.domain.model.Strategy
import com.ykb.app.cryptotrader.data.domain.model.StrategySettings
import com.ykb.app.cryptotrader.utils.enums.StrategyNames
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StrategyRepo : JpaRepository<Strategy, StrategyNames> {

    @Query("SELECT a FROM Strategy a WHERE a.name = :name AND a.settings = :settings")
    fun findByParams(name: StrategyNames, settings: StrategySettings): List<Strategy>

}