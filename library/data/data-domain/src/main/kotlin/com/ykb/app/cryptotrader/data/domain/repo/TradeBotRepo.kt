package com.ykb.app.cryptotrader.data.domain.repo

import com.ykb.app.cryptotrader.data.domain.model.TradeBot
import com.ykb.app.cryptotrader.utils.enums.TradeBotStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TradeBotRepo : JpaRepository<TradeBot, Int> {

    @Query("SELECT t FROM TradeBot t WHERE t.status = :status")
    fun findByStatus(status: TradeBotStatus): List<TradeBot>

}