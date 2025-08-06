package com.ykb.app.cryptotrader.web.api.rest

import com.ykb.app.cryptotrader.auth.base.BaseController
import com.ykb.app.cryptotrader.cloud.MsTraderClient
import com.ykb.app.cryptotrader.domaindto.FaultDto
import com.ykb.app.cryptotrader.domaindto.CreateTradeBotParamsDto
import com.ykb.app.cryptotrader.domaindto.base.Dto
import com.ykb.app.cryptotrader.utils.logger.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/api/bot")
class BotController(
    private val msTraderClient: MsTraderClient
) : BaseController() {

    override fun initLogger(): Logger {
        return Logger.getLogger(BotController::class)
    }

    @PutMapping("/create")
    fun create(@RequestBody requestDto: CreateTradeBotParamsDto): ResponseEntity<out Dto> {
        return try {
            msTraderClient.createTradeBot(getKey(), requestDto)
        } catch (ex: Exception) {
            handleException(FaultDto.Code.ERROR_001, ex)
        }
    }

    @PostMapping("/delete")
    fun delete(@RequestParam id: Int): ResponseEntity<out Dto> {
        return try {
            return msTraderClient.terminateTradeBot(getKey(), id)
        } catch (ex: Exception) {
            handleException(FaultDto.Code.ERROR_001, ex)
        }
    }

}