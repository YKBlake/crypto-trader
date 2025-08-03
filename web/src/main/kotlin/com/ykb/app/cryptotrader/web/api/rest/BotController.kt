package com.ykb.app.cryptotrader.web.api.rest

import com.ykb.app.cryptotrader.data.auth.model.User
import com.ykb.app.cryptotrader.domain.services.TradeBotManager
import com.ykb.app.cryptotrader.utils.enums.StrategyNames
import com.ykb.app.cryptotrader.web.dto.request.BotCreateRequestDto
import com.ykb.app.cryptotrader.web.dto.response.BotCreateResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/api/bot")
class BotController(
    private val tradeBotManager: TradeBotManager
) {

    @PutMapping("/create")
    fun create(@RequestBody requestDto: BotCreateRequestDto): ResponseEntity<BotCreateResponseDto> {
        val user = SecurityContextHolder.getContext().authentication!!.principal as User
        val params: Map<String, Any> =
            when (requestDto.strategyName) {
                StrategyNames.MACD if requestDto.macdSettings!=null -> requestDto.macdSettings.toMap()
                StrategyNames.RSI if requestDto.rsiSettings!=null -> requestDto.rsiSettings.toMap()
                else -> throw IllegalArgumentException("No strategy settings presented")
            }
        val tradeBot = tradeBotManager.create(user, requestDto.strategyName, params)
        return ResponseEntity.ok(BotCreateResponseDto(tradeBot))
    }

    @PostMapping("/delete")
    fun delete(@RequestParam id: Int): ResponseEntity<String> {
        if(!tradeBotManager.doTradeBotExist(id))
            throw IllegalArgumentException("There is no trade bot with id: $id")
        tradeBotManager.delete(id)
        return ResponseEntity.ok("SUCCESS")
    }

}