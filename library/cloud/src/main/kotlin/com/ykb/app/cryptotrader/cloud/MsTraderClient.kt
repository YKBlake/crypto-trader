package com.ykb.app.cryptotrader.cloud

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping

@FeignClient(name = "ms-trader", url = "http://localhost:8081")
interface MsTraderClient {

    @PutMapping("/api/ms/create-bot")
    fun createTradeBot(msKey: String): ResponseEntity<String>

}