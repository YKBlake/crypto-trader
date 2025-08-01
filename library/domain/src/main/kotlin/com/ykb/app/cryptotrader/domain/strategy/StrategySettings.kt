package com.ykb.app.cryptotrader.domain.strategy

abstract class StrategySettings(params: Map<String, String>) {

    init {
        init(params)
    }

    abstract fun init(params: Map<String, String>)

    abstract fun toJsonString(): String

}