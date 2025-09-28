package com.ykb.app.cryptotrader.data.dao;

import com.ykb.app.cryptotrader.data.model.TradeBotStateHistory;
import com.ykb.app.cryptotrader.data.repo.TradeBotStateHistoryRepo;

public final class TradeBotStateHistoryDao extends Dao<TradeBotStateHistory, TradeBotStateHistory.Key> {

    public TradeBotStateHistoryDao(TradeBotStateHistoryRepo repo) {
        super(repo);
    }

}
