package com.ykb.app.cryptotrader.data.model;

import com.ykb.app.cryptotrader.utils.enums.TradeBotStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TRADE_BOT_STATE_HISTORY")
@Data
@EqualsAndHashCode(callSuper = true)
public class TradeBotStateHistory extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRADE_BOT_ID", nullable = false)
    private TradeBot tradeBot;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeBotStatus state;

    public TradeBotStateHistory(TradeBot tradeBot, TradeBotStatus state) {
        this.tradeBot = tradeBot;
        this.state = state;
    }

}
