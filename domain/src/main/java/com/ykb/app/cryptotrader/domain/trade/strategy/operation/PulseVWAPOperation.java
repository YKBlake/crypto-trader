package com.ykb.app.cryptotrader.domain.trade.strategy.operation;

import com.ykb.app.cryptotrader.domain.binance.BinanceApi;
import com.ykb.app.cryptotrader.domain.trade.strategy.StrategySignal;
import com.ykb.app.cryptotrader.domain.trade.strategy.settings.PulseVWAPSettings;
import com.ykb.app.cryptotrader.domain.trade.strategy.settings.StrategySettings;

public class PulseVWAPOperation extends StrategyOperation<PulseVWAPSettings> {

    public PulseVWAPOperation(PulseVWAPSettings settings, BinanceApi api) {
        super(settings, api);
    }

    @Override
    public StrategySignal execute() {
        return null;
    }

}