package com.ykb.app.cryptotrader.trader.web.api.mw

import com.ykb.app.cryptotrader.data.auth.model.User
import com.ykb.app.cryptotrader.domain.services.TradeBotManager
import com.ykb.app.cryptotrader.domaindto.request.BotCreateRequestDto
import com.ykb.app.cryptotrader.domaindto.response.BotCreateResponseDto
import com.ykb.app.cryptotrader.domaindto.response.EmptyResponseDto
import com.ykb.app.cryptotrader.utils.enums.StrategyNames
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ms-client")
class MsController(
    private val tradeBotManager: TradeBotManager
) {

    @PutMapping("/create-bot")
    fun createTradeBot(requestDto: BotCreateRequestDto): ResponseEntity<BotCreateResponseDto> {
        val user = SecurityContextHolder.getContext().authentication!!.principal as User
        val params: Map<String, Any> =
            when (requestDto.strategyName) {
                StrategyNames.MACD if requestDto.macdSettings!=null -> requestDto.macdSettings!!.toMap()
                StrategyNames.RSI if requestDto.rsiSettings!=null -> requestDto.rsiSettings!!.toMap()
                else -> throw IllegalArgumentException("No strategy settings presented")
            }
        val tradeBot = tradeBotManager.create(user, requestDto.strategyName, params)
        return ResponseEntity.ok(BotCreateResponseDto(tradeBot))
    }

    @PostMapping("/terminate-bot")
    fun terminateTradeBot(id: Int): ResponseEntity<EmptyResponseDto> {
        if(!tradeBotManager.doTradeBotExist(id))
            throw IllegalArgumentException("There is no trade bot with id: $id")
        tradeBotManager.terminate(id)
        return ResponseEntity.ok(EmptyResponseDto())
    }

}