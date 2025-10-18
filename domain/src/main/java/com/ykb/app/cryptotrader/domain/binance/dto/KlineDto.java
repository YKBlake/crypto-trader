package com.ykb.app.cryptotrader.domain.binance.dto;

import com.ykb.app.cryptotrader.domain.binance.base.BinanceDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KlineDto implements BinanceDto {
    private final long openTime;
    private final String open;
    private final String high;
    private final String low;
    private final String close;
    private final String volume;
    private final long closeTime;
    private final String quoteAssetVolume;
    private final long numberOfTrades;
    private final String takerBuyBaseAssetVolume;
    private final String takerBuyQuoteAssetVolume;

    @Override
    public String toJsonString() {
        return """
        {
            "openTime": %d,
            "open": "%s",
            "high": "%s",
            "low": "%s",
            "close": "%s",
            "volume": "%s",
            "closeTime": %d,
            "quoteAssetVolume": "%s",
            "numberOfTrades": %d,
            "takerBuyBaseAssetVolume": "%s",
            "takerBuyQuoteAssetVolume": "%s"
        }
        """.formatted(
                openTime, open, high, low, close, volume, closeTime,
                quoteAssetVolume, numberOfTrades,
                takerBuyBaseAssetVolume, takerBuyQuoteAssetVolume
        );
    }
}