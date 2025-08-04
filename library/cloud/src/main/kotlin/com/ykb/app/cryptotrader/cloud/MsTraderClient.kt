package com.ykb.app.cryptotrader.cloud

import com.ykb.app.cryptotrader.domaindto.request.BotCreateRequestDto
import com.ykb.app.cryptotrader.domaindto.response.BotCreateResponseDto
import com.ykb.app.cryptotrader.domaindto.response.EmptyResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping

@FeignClient(name = "ms-trader", url = "http://localhost:8081")
interface MsTraderClient {

    @PutMapping("/api/ms-client/create-bot")
    fun createTradeBot(requestDto: BotCreateRequestDto): ResponseEntity<BotCreateResponseDto>

    @PostMapping("/api/ms-client/terminate-bot")
    fun terminateTradeBot(id: Int): ResponseEntity<EmptyResponseDto>

}