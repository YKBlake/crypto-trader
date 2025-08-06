package com.ykb.app.cryptotrader.auth.base

import com.ykb.app.cryptotrader.domaindto.FaultDto
import com.ykb.app.cryptotrader.utils.exceptions.auth.BadRequestException
import com.ykb.app.cryptotrader.utils.logger.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity

abstract class BaseController {

    protected val log = initLogger()

    @Value($$"${app.properties.microservice.key}")
    private lateinit var key: String

    protected abstract fun initLogger(): Logger

    protected fun getKey(): String = key

    protected fun handleException(code: FaultDto.Code, ex: Exception): ResponseEntity<FaultDto> {
        log.error(ex)
        val faultDto = FaultDto(code)
        return when (ex) {
            is BadRequestException ->
                ResponseEntity.badRequest().body(faultDto)
            else ->
                ResponseEntity.internalServerError().body(faultDto)
        }
    }

}