package com.ykb.app.cryptotrader.domain.binance;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.futures.client.utils.ProxyAuth;
import com.ykb.app.cryptotrader.domain.binance.converter.KlineDtoConverter;
import com.ykb.app.cryptotrader.domain.binance.dto.KlineDto;
import com.ykb.app.cryptotrader.domain.component.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.net.Proxy;
import java.util.*;

@Component
public class BinanceApi {

    private final Logger log = Logger.get(BinanceApi.class);

    public static final String PROD_FAPI_BASE = "https://fapi.binance.com";
    public static final String TESTNET_FAPI_BASE = "https://testnet.binancefuture.com";

    private final UMFuturesClientImpl client;

    /**
     * @param apiKey   Your Binance API key (can be null for public endpoints only)
     * @param secret   Your Binance API secret (can be null for public endpoints only)
     * @param testnet  true to use futures testnet; false for production
     */
    public BinanceApi(String apiKey, String secret, boolean testnet) {
        String baseUrl = testnet ? TESTNET_FAPI_BASE : PROD_FAPI_BASE;
        // If you pass null/empty apiKey/secret, only public endpoints (e.g., klines) will work.
        this.client = (apiKey != null && secret != null)
                ? new UMFuturesClientImpl(apiKey, secret, baseUrl)
                : new UMFuturesClientImpl(baseUrl);
        // Optional: expose limit usage headers in responses for observability
        this.client.setShowLimitUsage(true);
    }

    /**
     * @param testnet  true to use futures testnet; false for production
     */
    public BinanceApi(boolean testnet) {
        this(null, null, testnet);
    }

    public BinanceApi() {
        this(null, null, false);
    }

    /**
     * Optionally set an HTTP proxy. Call unsetProxy() to remove.
     */
    public void setProxy(Proxy proxy, okhttp3.Authenticator auth) {
        this.client.setProxy(new ProxyAuth(proxy, auth));
    }

    /** Remove proxy previously set via setProxy. */
    public void unsetProxy() {
        this.client.unsetProxy();
    }

    /**
     * Fetches USDⓈ-M Futures kline (candlestick) data from Binance API.
     *
     * <p>The request parameters are provided via a {@link LinkedHashMap} where the keys
     * correspond to constants defined in {@link MethodParams.GetKlines}.
     * Required and optional parameters include:
     *
     * <ul>
     *   <li><b>SYMBOL</b> – Trading pair symbol (e.g. "BTCUSDT"). Required.</li>
     *   <li><b>INTERVAL</b> – Candlestick interval (e.g. "1m", "5m", "1h", "4h", "1d"). Required.</li>
     *   <li><b>START_TIME</b> – Start time in epoch milliseconds (inclusive). Optional.</li>
     *   <li><b>END_TIME</b> – End time in epoch milliseconds (exclusive). Optional.</li>
     *   <li><b>LIMIT</b> – Maximum number of klines to return (1–1500, default 500). Optional.</li>
     * </ul>
     *
     * @param params a {@code LinkedHashMap<String, Object>} containing the request parameters.
     * @return a list of {@link KlineDto} objects representing parsed kline data,
     *         or {@code null} if an error occurs.
     * @throws RuntimeException if validation or parsing fails.
     */

    public List<KlineDto> getKlines(LinkedHashMap<String, Object> params) {
        try {
            Object symbol = params.get(MethodParams.GetKlines.SYMBOL);
            Object interval = params.get(MethodParams.GetKlines.INTERVAL);
            Objects.requireNonNull(symbol, "Symbol can`t be blank");
            Assert.hasText(String.valueOf(symbol), "Symbol can`t be blank");
            Objects.requireNonNull(interval, "Interval can`t be blank");
            Assert.hasText(String.valueOf(interval), "Interval can`t be blank");

            String json = client.market().klines(params);
            return KlineDtoConverter.getInstance().convert(json);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    // ======================================================================
    // ORDERS (USDⓈ-M Futures)
    // ======================================================================

    /**
     * Place a LIVE futures order (e.g., LIMIT/MARKET). For a dry run, use testNewOrder.
     *
     * Required fields vary by order type; at minimum:
     * @param symbol       e.g., "BTCUSDT"
     * @param side         "BUY" or "SELL"
     * @param type         "MARKET", "LIMIT", "STOP", "STOP_MARKET", "TAKE_PROFIT", "TAKE_PROFIT_MARKET", etc.
     * @param quantity     base asset qty (nullable if using quoteQty for MARKET orders)
     * @param price        price for LIMIT/STOP/TP (nullable for MARKET)
     * @param timeInForce  "GTC", "IOC", "FOK" (required for LIMIT)
     * @param reduceOnly   optional (true/false)
     * @param closePosition optional (true/false) – for close-all on TP/SL market types
     * @param extra        any additional raw params (e.g., "positionSide","workingType","priceProtect")
     * @return raw JSON from Binance
     */
    public String newOrder(
            String symbol,
            String side,
            String type,
            BigDecimal quantity,
            BigDecimal price,
            String timeInForce,
            Boolean reduceOnly,
            Boolean closePosition,
            LinkedHashMap<String, Object> extra
    ) {
        Map<String, Object> p = new LinkedHashMap<>();
        p.put("symbol", symbol);
        p.put("side", side);
        p.put("type", type);
        if (quantity != null) p.put("quantity", quantity);
        if (price != null)    p.put("price", price);
        if (timeInForce != null) p.put("timeInForce", timeInForce);
        if (reduceOnly != null) p.put("reduceOnly", reduceOnly);
        if (closePosition != null) p.put("closePosition", closePosition);
        if (extra != null) p.putAll(extra);

//        return client.trade().newOrder(p);
        return null;
    }

    /**
     * Test (validate) an order without actually placing it on the exchange.
     * Binance validates parameters and signature.
     */
    public String testNewOrder(
            String symbol,
            String side,
            String type,
            BigDecimal quantity,
            BigDecimal price,
            String timeInForce,
            Boolean reduceOnly,
            Boolean closePosition,
            LinkedHashMap<String, Object> extra
    ) {
        LinkedHashMap<String, Object> p = new LinkedHashMap<>();
        p.put("symbol", symbol);
        p.put("side", side);
        p.put("type", type);
        if (quantity != null) p.put("quantity", quantity);
        if (price != null)    p.put("price", price);
        if (timeInForce != null) p.put("timeInForce", timeInForce);
        if (reduceOnly != null) p.put("reduceOnly", reduceOnly);
        if (closePosition != null) p.put("closePosition", closePosition);
        if (extra != null) p.putAll(extra);

//        return client.trade().testNewOrder(p);
        return null;
    }

    public static class MethodParams {
        public static class GetKlines {
            public static final String SYMBOL = "symbol";
            public static final String INTERVAL = "interval";
            public static final String START_TIME = "startTime";
            public static final String END_TIME = "endTime";
            public static final String LIMIT = "limit";
        }
    }

}
