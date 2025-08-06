package com.ykb.app.cryptotrader.domaindto

import com.ykb.app.cryptotrader.domaindto.base.Dto

open class FaultDto(
    faultCode: Code
) : Dto {

    val code: String = faultCode.toString()
    val description: String = faultCode.desc

    enum class Code(val desc: String) {
        ERROR_001("Generic error occurred")
    }

}