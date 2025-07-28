package com.ykb.app.cryptotrader.data.domain.repo

import com.ykb.app.cryptotrader.data.domain.model.Strategy
import com.ykb.app.cryptotrader.domain.strategy.StrategyNames
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StrategyRepo : JpaRepository<Strategy, StrategyNames>