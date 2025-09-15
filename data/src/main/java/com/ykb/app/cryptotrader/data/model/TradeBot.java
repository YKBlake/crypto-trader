package com.ykb.app.cryptotrader.data.model;

import com.ykb.app.cryptotrader.utils.enums.TradeBotStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TRADE_BOT")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TradeBot extends BaseIdEntity {

    @ManyToOne
    @JoinColumn(name = "STRATEGY_ID", nullable = false)
    private Strategy strategy;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "STATUS", columnDefinition = "VARCHAR(16)")
    @Enumerated(EnumType.STRING)
    private TradeBotStatus status = TradeBotStatus.INIT;

    public boolean isRunning() {
        return TradeBotStatus.RUNNING==status;
    }

    public boolean isTerminated() {
        return TradeBotStatus.TERMINATED==status;
    }

    public void start() {
        status = TradeBotStatus.RUNNING;
    }

    public void enterTrade() {
        status = TradeBotStatus.IN_TRADE;
    }

    public void exitTrade() {
        start();
    }

    public void terminate() {
        status = TradeBotStatus.TERMINATED;
        inactivate();
    }

}
