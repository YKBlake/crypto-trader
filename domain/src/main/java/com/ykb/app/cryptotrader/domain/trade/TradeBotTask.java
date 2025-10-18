package com.ykb.app.cryptotrader.domain.trade;

import com.ykb.app.cryptotrader.data.dao.TradeBotDao;
import com.ykb.app.cryptotrader.data.model.TradeBot;
import com.ykb.app.cryptotrader.domain.binance.BinanceApi;
import com.ykb.app.cryptotrader.domain.component.Logger;
import com.ykb.app.cryptotrader.domain.trade.strategy.StrategyOperator;
import com.ykb.app.cryptotrader.domain.trade.strategy.StrategySignal;
import com.ykb.app.cryptotrader.utils.enums.TradeBotStatus;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

public class TradeBotTask implements Runnable {

    private final Logger logger = Logger.get(TradeBotTask.class);

    private final TradeBot tradeBot;
    private final StrategyOperator strategyOperator;
    private final BinanceApi binanceApi;
    private final TradeBotDao tradeBotDao;
    private final AtomicBoolean stop;

    private boolean shouldWait = true;

    public TradeBotTask(TradeBot tradeBot, BinanceApi binanceApi, TradeBotDao tradeBotDao, AtomicBoolean stop) {
        this.tradeBot = tradeBot;
        this.binanceApi = binanceApi;
        this.tradeBotDao = tradeBotDao;
        this.strategyOperator = new StrategyOperator(tradeBot.getStrategyName(), tradeBot.getStrategySettings(), binanceApi);
        this.stop = stop;
    }

    @Override
    public void run() {
        try {
            while (!stop.get()) {
                if (Thread.interrupted())
                    break;

                switch (tradeBot.getStatus()) {
                    case INIT -> init();
                    case RUNNING -> running();
                    case ENTERING_LONG -> enteringLong();
                    case ENTERING_SHORT -> enteringShort();
                    case IN_LONG -> inLong();
                    case IN_SHORT -> inShort();
                    case EXITING_LONG -> exitingLong();
                    case EXITING_SHORT -> exitingShort();
                    case TERMINATED -> throw new UnsupportedOperationException("Task can`t run on a terminated bot");
                }

                tradeBotDao.save(tradeBot);

                if(!shouldWait) {
                    shouldWait = true;
                    continue;
                }

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

    private void init() {
        tradeBot.setStatus(TradeBotStatus.RUNNING);
        shouldWait = false;
    }

    private void running() throws InterruptedException {
        StrategySignal signal = strategyOperator.retrieveSignal();
        // TODO
    }

    private void enteringLong() throws InterruptedException {
        // TODO
        tradeBot.setStatus(TradeBotStatus.IN_LONG);
    }

    private void inLong() throws InterruptedException {
        // TODO
    }

    private void exitingLong() throws InterruptedException {
        // TODO
        tradeBot.setStatus(TradeBotStatus.RUNNING);
    }

    private void enteringShort() throws InterruptedException {
        // TODO
        tradeBot.setStatus(TradeBotStatus.IN_SHORT);
    }

    private void inShort() throws InterruptedException {
        // TODO
    }

    private void exitingShort() throws InterruptedException {
        // TODO
        tradeBot.setStatus(TradeBotStatus.RUNNING);
    }

    public long id() {
        return tradeBot.getId();
    }

}
