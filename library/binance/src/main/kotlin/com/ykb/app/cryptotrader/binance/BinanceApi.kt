package com.ykb.app.cryptotrader.binance

import com.binance.connector.client.impl.SpotClientImpl
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ykb.app.cryptotrader.binance.dto.KlineDto
import org.springframework.stereotype.Service

@Service
class BinanceApi(private val credentials: Credentials?) {

    private val client: SpotClientImpl

    constructor() : this(null)
    constructor(apiKey: String, secretKey: String) : this(Credentials(apiKey, secretKey))

    init {
        client =
            if(loggedIn())
                SpotClientImpl(credentials!!.apiKey, credentials.secretKey)
            else
                SpotClientImpl()
    }

    fun buyLimit(symbol: String, quantity: String, price: String): String {
        val params = mapOf(
            ApiParams.SYMBOL to symbol,
            ApiParams.SIDE to "BUY",
            ApiParams.TYPE to "LIMIT",
            ApiParams.TIME_IN_FORCE to "GTC",
            ApiParams.QUANTITY to quantity,
            ApiParams.PRICE to price
        )
        return client.createTrade().newOrder(params)
    }

    fun sellLimit(symbol: String, quantity: String, price: String): String {
        val params = mapOf(
            ApiParams.SYMBOL to symbol,
            ApiParams.SIDE to "SELL",
            ApiParams.TYPE to "LIMIT",
            ApiParams.TIME_IN_FORCE to "GTC",
            ApiParams.QUANTITY to quantity,
            ApiParams.PRICE to price
        )
        return client.createTrade().newOrder(params)
    }

    fun stopLossOrder(symbol: String, quantity: String, stopPrice: String, price: String): String {
        val params = mapOf(
            ApiParams.SYMBOL to symbol,
            ApiParams.SIDE to "SELL",
            ApiParams.TYPE to "STOP_LOSS_LIMIT",
            ApiParams.TIME_IN_FORCE to "GTC",
            ApiParams.QUANTITY to quantity,
            ApiParams.PRICE to price,
            ApiParams.STOP_PRICE to stopPrice
        )
        return client.createTrade().newOrder(params)
    }

    fun getKlines(symbol: String, interval: String, limit: Int = 500): List<KlineDto> {
        val params = mapOf(
            ApiParams.SYMBOL to symbol,
            ApiParams.INTERVAL to interval,
            ApiParams.LIMIT to limit.toString()
        )
        val klinesJson = client.createMarket().klines(params)

        val mapper = jacksonObjectMapper()
        val rawList: List<List<Any>> = mapper.readValue(klinesJson)

        return rawList.map { k ->
            KlineDto(
                openTime = (k[0] as Number).toLong(),
                open = k[1].toString(),
                high = k[2].toString(),
                low = k[3].toString(),
                close = k[4].toString(),
                volume = k[5].toString(),
                closeTime = (k[6] as Number).toLong(),
                quoteAssetVolume = k[7].toString(),
                numberOfTrades = (k[8] as Number).toInt(),
                takerBuyBaseAssetVolume = k[9].toString(),
                takerBuyQuoteAssetVolume = k[10].toString()
            )
        }
    }

    fun getAllTradableAssets(): List<String> {
        val exchangeInfo = client.createMarket().exchangeInfo(mapOf())
        val regex = """"symbol"\s*:\s*"([A-Z]+USDT)"""".toRegex()
        return regex.findAll(exchangeInfo).map { it.groupValues[1] }.toList()
    }

    data class Credentials(val apiKey: String, val secretKey: String)

    fun loggedIn(): Boolean {
        return credentials!=null
    }

}