package com.ykb.app.cryptotrader.domain.model

data class WrappedTradeBot(
    val id: Int,
    val task: TradeBotTask,
    val thread: Thread
)