package com.ykb.app.cryptotrader.domain.binance.base;

import java.util.List;

public interface BinanceConverter<T extends BinanceDto> {

    List<T> convert(String json);

}