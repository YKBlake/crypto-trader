package com.ykb.app.cryptotrader.domain.trade;

import com.ykb.app.cryptotrader.data.dao.TradeBotDao;
import com.ykb.app.cryptotrader.data.model.TradeBot;
import com.ykb.app.cryptotrader.domain.binance.BinanceApi;
import com.ykb.app.cryptotrader.domain.trade.strategy.StrategyOperator;
import com.ykb.app.cryptotrader.domain.trade.strategy.StrategySignal;

public class TradeBotThread extends Thread {

    private final TradeBot tradeBot;
    private final StrategyOperator strategyOperator;
    private final BinanceApi binanceApi;
    private final TradeBotDao tradeBotDao;
    private boolean isTerminated = false;

    public TradeBotThread(TradeBot tradeBot, BinanceApi binanceApi, TradeBotDao tradeBotDao) {
        this.tradeBot = tradeBot;
        this.binanceApi = binanceApi;
        this.tradeBotDao = tradeBotDao;
        this.strategyOperator = new StrategyOperator(tradeBot.getStrategy(), binanceApi);
    }

    @Override
    public void run() {
        while(!isTerminated) {
            StrategySignal signal = strategyOperator.retrieveSignal();
            if(signal.equals(StrategySignal.BUY))
                binanceApi.buy();
        }
        tradeBot.terminate();
        tradeBotDao.save(tradeBot);
    }

    public void terminate() {
        isTerminated = true;
    }

    public long getTradeBotId() {
        return tradeBot.getId();
    }

}
