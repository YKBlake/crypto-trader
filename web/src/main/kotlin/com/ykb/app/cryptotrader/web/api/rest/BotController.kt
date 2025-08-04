package com.ykb.app.cryptotrader.web.api.rest

import com.ykb.app.cryptotrader.cloud.MsTraderClient
import com.ykb.app.cryptotrader.data.auth.model.User
import com.ykb.app.cryptotrader.utils.enums.StrategyNames
import com.ykb.app.cryptotrader.domaindto.request.BotCreateRequestDto
import com.ykb.app.cryptotrader.domaindto.response.BotCreateResponseDto
import com.ykb.app.cryptotrader.domaindto.response.EmptyResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/api/bot")
class BotController(
    private val msTraderClient: MsTraderClient
) {

    @PutMapping("/create")
    fun create(@RequestBody requestDto: BotCreateRequestDto): ResponseEntity<BotCreateResponseDto> {
        return msTraderClient.createTradeBot(requestDto)
    }

    @PostMapping("/delete")
    fun delete(@RequestParam id: Int): ResponseEntity<EmptyResponseDto> {
        return msTraderClient.terminateTradeBot(id)
    }

}