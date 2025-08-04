package com.ykb.app.cryptotrader.domaindto.response

import com.ykb.app.cryptotrader.data.domain.model.TradeBot
import com.ykb.app.cryptotrader.domaindto.base.Dto
import com.ykb.app.cryptotrader.domaindto.base.FaultDto

class BotCreateResponseDto(
    tradeBot: TradeBot?,
    faultDto: FaultDto?
) : Dto(faultDto) {

    constructor(tradeBot: TradeBot) : this(tradeBot, null)
    constructor(faultDto: FaultDto) : this(null, faultDto)

    val tradeBotId = tradeBot?.getId()

}