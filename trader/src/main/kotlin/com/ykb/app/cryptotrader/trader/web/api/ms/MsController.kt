package com.ykb.app.cryptotrader.trader.web.api.ms

import com.ykb.app.cryptotrader.cloud.BaseMsController
import com.ykb.app.cryptotrader.data.auth.model.User
import com.ykb.app.cryptotrader.domain.services.TradeBotManager
import com.ykb.app.cryptotrader.domaindto.CreateTradeBotParamsDto
import com.ykb.app.cryptotrader.domaindto.TradeBotIdDto
import com.ykb.app.cryptotrader.domaindto.base.Dto
import com.ykb.app.cryptotrader.utils.enums.StrategyNames
import com.ykb.app.cryptotrader.utils.exceptions.auth.BadRequestException
import org.springframework.http.HttpStatus
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
) : BaseMsController() {

    @PutMapping("/create-bot")
    fun createTradeBot(key: String, requestDto: CreateTradeBotParamsDto): ResponseEntity<TradeBotIdDto> {
        validateKey(key)
        val user = SecurityContextHolder.getContext().authentication!!.principal as User
        val params: Map<String, Any> =
            when (requestDto.strategyName) {
                StrategyNames.MACD if requestDto.macdSettings != null -> requestDto.macdSettings!!.toMap()
                StrategyNames.RSI if requestDto.rsiSettings != null -> requestDto.rsiSettings!!.toMap()
                else -> throw BadRequestException("No strategy settings presented")
            }
        val tradeBot = tradeBotManager.create(user, requestDto.strategyName, params)
        return ResponseEntity.ok(TradeBotIdDto(tradeBot.getId()))
    }

    @PostMapping("/terminate-bot")
    fun terminateTradeBot(key: String, id: Int): ResponseEntity<out Dto> {
        validateKey(key)
        if(!tradeBotManager.doTradeBotExist(id))
            throw BadRequestException("There is no trade bot with id: $id")
        tradeBotManager.terminate(id)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

}