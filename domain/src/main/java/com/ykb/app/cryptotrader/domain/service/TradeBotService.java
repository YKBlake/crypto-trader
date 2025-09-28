package com.ykb.app.cryptotrader.domain.service;

import com.ykb.app.cryptotrader.data.dao.TradeBotDao;
import com.ykb.app.cryptotrader.data.model.Strategy;
import com.ykb.app.cryptotrader.data.model.TradeBot;
import com.ykb.app.cryptotrader.data.model.User;
import com.ykb.app.cryptotrader.domain.binance.BinanceApi;
import com.ykb.app.cryptotrader.domain.trade.TradeBotTask;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class TradeBotService {

    private final TradeBotDao tradeBotDao;
    private final BinanceApi binanceApi;

    private final ConcurrentMap<Long, WrappedTradeBotTask> tradeBotTasks = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    @PostConstruct
    private void initTradeBotThreads() {
        List<TradeBot> tradeBots = tradeBotDao.findAllNonTerminated();
        if(tradeBots==null)
            return;

        tradeBots.forEach(this::startBotIfNotRunning);
    }

    public long create(User user, Strategy strategy) {
        TradeBot tradeBot = new TradeBot(user, strategy);
        tradeBot = tradeBotDao.save(tradeBot);
        startBotIfNotRunning(tradeBot);
        return tradeBot.getId();
    }

    public void terminate(long id) {
        WrappedTradeBotTask wrappedTask = tradeBotTasks.get(id);
        if (wrappedTask == null)
            throw new IllegalArgumentException("Trade bot not found or not running: " + id);

        wrappedTask.stop.set(true);
        Future<?> task = wrappedTask.task;
        if (task != null) {
            // Interrupt if running; if queued, remove from queue
            task.cancel(true);
        }

        tradeBotTasks.remove(id);
    }

    private void startBotIfNotRunning(TradeBot tradeBot) {
        long id = tradeBot.getId();
        tradeBotTasks.computeIfAbsent(id, __ -> {
            AtomicBoolean stop = new AtomicBoolean(false);
            TradeBotTask tradeBotTask = new TradeBotTask(tradeBot, binanceApi, tradeBotDao, stop);
            Future<?> task = executor.submit(tradeBotTask);
            return new WrappedTradeBotTask(task, stop);
        });
    }

    /** Optional helper: query status */
    public boolean isRunning(long id) {
        WrappedTradeBotTask wrappedTask = tradeBotTasks.get(id);
        if(wrappedTask == null)
            throw new IllegalArgumentException("Trade bot not found: " + id);

        Future<?> task = wrappedTask.task;
        return task != null && !task.isDone() && !task.isCancelled();
    }

    private record WrappedTradeBotTask(Future<?> task, AtomicBoolean stop) {}

}
