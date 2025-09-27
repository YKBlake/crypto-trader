package com.ykb.app.cryptotrader.domain;

import com.ykb.app.cryptotrader.data.dao.TradeBotDao;
import com.ykb.app.cryptotrader.data.model.TradeBot;
import com.ykb.app.cryptotrader.domain.binance.BinanceApi;
import com.ykb.app.cryptotrader.domain.trade.TradeBotThread;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TradeBotService {

    private final List<TradeBotThread> tradeBotThreads = new LinkedList<>();
    private final TradeBotDao tradeBotDao;
    private final BinanceApi binanceApi;

    public TradeBotService(TradeBotDao tradeBotDao, BinanceApi binanceApi) {
        this.tradeBotDao=tradeBotDao;
        this.binanceApi=binanceApi;
    }

    @PostConstruct
    private void initTradeBotThreads() {
        List<TradeBot> tradeBots = tradeBotDao.findAllNonTerminated();
        if(tradeBots==null)
            return;

        tradeBots.forEach(tradeBot -> {
            TradeBotThread thread = new TradeBotThread(
                    tradeBot,
                    binanceApi,
                    tradeBotDao
            );
            thread.start();
            tradeBotThreads.add(thread);
        });
    }

    public long create() {
        return 1L;
    }

    public void terminate(long id) {
        TradeBotThread thread = tradeBotThreads.stream()
                .filter(t -> id==t.getTradeBotId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Trade bot not found with id " + id));
        thread.terminate();
    }

}
