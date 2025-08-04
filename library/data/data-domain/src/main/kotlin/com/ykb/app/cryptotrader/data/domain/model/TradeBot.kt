package com.ykb.app.cryptotrader.data.domain.model

import com.ykb.app.cryptotrader.data.auth.model.User
import com.ykb.app.cryptotrader.data.base.entity.BaseIdEntity
import com.ykb.app.cryptotrader.utils.enums.TradeBotStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
    private val user: User,

    @Column(name = "STATUS", columnDefinition = "VARCHAR(16)")
    @Enumerated(EnumType.STRING)
    private var status: TradeBotStatus = TradeBotStatus.ACTIVE
) : BaseIdEntity() {

    fun getStrategy(): Strategy {
        return strategy
    }

    fun getUser(): User {
        return user
    }

    fun isActive(): Boolean {
        return TradeBotStatus.ACTIVE==status
    }

    fun isTerminated(): Boolean {
        return TradeBotStatus.TERMINATED==status
    }

    fun terminate() {
        status = TradeBotStatus.TERMINATED
        inactivate()
    }

}