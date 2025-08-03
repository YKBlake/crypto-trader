package com.ykb.app.cryptotrader.web.dto.response

import com.ykb.app.cryptotrader.data.domain.model.TradeBot

class BotCreateResponseDto(
    tradeBot: TradeBot
) {

    val tradeBotId = tradeBot.getId()

}