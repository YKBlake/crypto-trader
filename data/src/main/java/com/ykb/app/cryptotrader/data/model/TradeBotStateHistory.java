package com.ykb.app.cryptotrader.data.model;

import com.ykb.app.cryptotrader.utils.enums.TradeBotStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TRADE_BOT_STATE_HISTORY")
public class TradeBotStateHistory {

    @EmbeddedId
    private Key id;

    @Column(nullable = false)
    @Getter
    @Setter
    private TradeBotStatus state;

    public TradeBotStateHistory(TradeBot tradeBot, TradeBotStatus state) {
        id = new Key(tradeBot);
        this.state = state;
    }

    public TradeBot getTradeBot() {
        return id.tradeBot;
    }

    public void setTradeBot(TradeBot tradeBot) {
        id.tradeBot = tradeBot;
    }

    public Date getCreationTime() {
        return id.creationTime;
    }

    public void setCreationTime(Date creationTime) {
        id.creationTime = creationTime;
    }

    @Embeddable
    @Data
    public static class Key implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "TRADE_BOT_ID", nullable = false)
        private TradeBot tradeBot;

        @Column(name = "CREATION_TIME", nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private Date creationTime = new Date();

        public Key(TradeBot tradeBot) {
            this.tradeBot = tradeBot;
        }
    }

}
