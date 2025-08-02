package com.ykb.app.cryptotrader.data.domain.model

import com.ykb.app.cryptotrader.data.auth.model.User
import com.ykb.app.cryptotrader.data.base.entity.BaseIdEntity
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "TRADE_BOT")
class TradeBot(
    @ManyToOne
    @JoinColumn(name = "STRATEGY_ID", nullable = false)
    private val strategy: Strategy,

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private val user: User
) : BaseIdEntity()