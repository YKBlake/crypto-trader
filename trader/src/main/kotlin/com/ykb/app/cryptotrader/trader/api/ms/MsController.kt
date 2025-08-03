package com.ykb.app.cryptotrader.trader.api.ms

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ms")
class MsController {

    @PutMapping("/create-bot")
    fun createTradeBot(): ResponseEntity<String> {
        return ResponseEntity.ok("")
    }

}