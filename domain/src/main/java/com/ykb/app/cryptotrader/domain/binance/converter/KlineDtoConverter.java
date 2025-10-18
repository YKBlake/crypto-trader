package com.ykb.app.cryptotrader.domain.binance.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ykb.app.cryptotrader.domain.binance.base.BinanceConverter;
import com.ykb.app.cryptotrader.domain.binance.dto.KlineDto;
import com.ykb.app.cryptotrader.domain.component.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Binance kline array format (USDⓈ-M /fapi):
 * 0 openTime, 1 open, 2 high, 3 low, 4 close, 5 volume,
 * 6 closeTime, 7 quoteAssetVolume, 8 numberOfTrades,
 * 9 takerBuyBaseAssetVolume, 10 takerBuyQuoteAssetVolume, 11 ignore
 */
public class KlineDtoConverter implements BinanceConverter<KlineDto> {

    private final Logger log = Logger.get(KlineDtoConverter.class);

    private static KlineDtoConverter converter;

    private KlineDtoConverter() {}

    public static KlineDtoConverter getInstance() {
        if(converter==null)
            converter=new KlineDtoConverter();
        return converter;
    }

    @Override
    public List<KlineDto> convert(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(json);
            JsonNode data = mapper.readTree(root.get("data").textValue());
            if (data == null || !data.isArray()) {
                throw new IllegalArgumentException("Invalid JSON: 'data' array not found");
            }

            List<KlineDto> out = new ArrayList<>();
            for (JsonNode row : data) {
                if (!row.isArray() || row.size() < 11)
                    throw new IllegalArgumentException("Invalid kline row: " + row);

                KlineDto dto = KlineDto.builder()
                        .openTime(row.get(0).asLong())
                        .open(row.get(1).asText())
                        .high(row.get(2).asText())
                        .low(row.get(3).asText())
                        .close(row.get(4).asText())
                        .volume(row.get(5).asText())
                        .closeTime(row.get(6).asLong())
                        .quoteAssetVolume(row.get(7).asText())
                        .numberOfTrades(row.get(8).asLong())
                        .takerBuyBaseAssetVolume(row.get(9).asText())
                        .takerBuyQuoteAssetVolume(row.get(10).asText())
                        .build();

                out.add(dto);
            }

            return out;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

}