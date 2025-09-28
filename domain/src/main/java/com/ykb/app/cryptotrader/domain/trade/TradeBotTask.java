package com.ykb.app.cryptotrader.domain.trade;

import com.ykb.app.cryptotrader.data.dao.TradeBotDao;
import com.ykb.app.cryptotrader.data.model.TradeBot;
import com.ykb.app.cryptotrader.domain.binance.BinanceApi;
import com.ykb.app.cryptotrader.domain.component.Logger;
import com.ykb.app.cryptotrader.domain.trade.strategy.StrategyOperator;
import com.ykb.app.cryptotrader.domain.trade.strategy.StrategySignal;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

public class TradeBotTask implements Runnable {

    private final Logger logger = Logger.get(TradeBotTask.class);

    private final TradeBot tradeBot;
    private final StrategyOperator strategyOperator;
    private final BinanceApi binanceApi;
    private final TradeBotDao tradeBotDao;
    private final AtomicBoolean stop;

    public TradeBotTask(TradeBot tradeBot, BinanceApi binanceApi, TradeBotDao tradeBotDao, AtomicBoolean stop) {
        this.tradeBot = tradeBot;
        this.binanceApi = binanceApi;
        this.tradeBotDao = tradeBotDao;
        this.strategyOperator = new StrategyOperator(tradeBot.getStrategy(), binanceApi);
        this.stop = stop;
    }

    @Override
    public void run() {
        try {
            while (!stop.get()) {
                if (Thread.interrupted()) {
                    break;
                }

                StrategySignal signal = strategyOperator.retrieveSignal();
                if (signal.equals(StrategySignal.BUY))
                    binanceApi.buy();

                Thread.sleep(Duration.ofSeconds(1));
            }
        } catch (InterruptedException ie) {
            // Preserve interrupt status and exit gracefully
            logger.error(ie);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            // Log & decide: retry? mark failed? For now, mark as terminated-with-error.
            logger.error(e);
        } finally {
            tradeBot.terminate();
            tradeBotDao.save(tradeBot);
        }
    }

    public long id() {
        return tradeBot.getId();
    }

}
