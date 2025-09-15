package com.ykb.app.cryptotrader.data.dao;

import com.ykb.app.cryptotrader.data.model.TradeBot;
import com.ykb.app.cryptotrader.data.repo.TradeBotRepo;
import org.springframework.stereotype.Component;

@Component
public final class TradeBotDao extends Dao<TradeBot, Long> {

    public TradeBotDao(TradeBotRepo repo) {
        super(repo);
    }

}
