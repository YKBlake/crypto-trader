package com.ykb.app.cryptotrader.cloud

import com.ykb.app.cryptotrader.domaindto.CreateTradeBotParamsDto
import com.ykb.app.cryptotrader.domaindto.TradeBotIdDto
import com.ykb.app.cryptotrader.domaindto.base.Dto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping

@FeignClient(name = "ms-trader", url = "http://localhost:8081")
interface MsTraderClient {

    @PutMapping("/api/ms-client/create-bot")
    fun createTradeBot(key: String, requestDto: CreateTradeBotParamsDto): ResponseEntity<TradeBotIdDto>

    @PostMapping("/api/ms-client/terminate-bot")
    fun terminateTradeBot(key: String, id: Int): ResponseEntity<out Dto>

}