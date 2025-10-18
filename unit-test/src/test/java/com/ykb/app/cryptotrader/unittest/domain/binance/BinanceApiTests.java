package com.ykb.app.cryptotrader.unittest.domain.binance;

import com.ykb.app.cryptotrader.domain.binance.BinanceApi;
import com.ykb.app.cryptotrader.domain.binance.dto.KlineDto;
import com.ykb.app.cryptotrader.domain.component.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.List;

@SpringBootTest
public class BinanceApiTests {

    private final Logger log = Logger.get(BinanceApiTests.class);
    private final BinanceApi binanceApi;

    public BinanceApiTests() {
        binanceApi = new BinanceApi();
    }

    @Test
    public void getKline() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put(BinanceApi.MethodParams.GetKlines.SYMBOL, "BTCUSDT");
        params.put(BinanceApi.MethodParams.GetKlines.INTERVAL, "1h");
        List<KlineDto> klines = binanceApi.getKlines(params);
        Assert.notNull(klines, "Failed to retrieve kline");
        Assert.notEmpty(klines, "No Kline found");
        klines.forEach(k -> log.info(k.toJsonString()));
    }

}
