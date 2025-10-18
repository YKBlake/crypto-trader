package com.ykb.app.cryptotrader.data.model;

import com.ykb.app.cryptotrader.utils.enums.StrategyNames;
import com.ykb.app.cryptotrader.utils.enums.TradeBotStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "TRADE_BOT")
@Data
@EqualsAndHashCode(callSuper = true)
public class TradeBot extends BaseIdEntity {

    @Column(name = "NAME", columnDefinition = "VARCHAR(256)", nullable = false)
    @Enumerated(EnumType.STRING)
    private StrategyNames strategyName;

    @Column(name = "STRATEGY_SETTINGS", columnDefinition = "VARCHAR(2048)", nullable = false)
    private String strategySettings;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "STATUS", columnDefinition = "VARCHAR(16)")
    @Enumerated(EnumType.STRING)
    private TradeBotStatus status = TradeBotStatus.INIT;

    @OneToMany(mappedBy = "tradeBot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TradeBotStateHistory> stateHistory;

    public TradeBot(User user, StrategyNames strategyName, String strategySettings) {
        this.user=user;
        this.strategyName=strategyName;
        this.strategySettings=strategySettings;
    }

    public boolean isInit() {
        return TradeBotStatus.INIT==status;
    }

    public boolean isRunning() {
        return TradeBotStatus.isRunning(status);
    }

    public boolean isTerminated() {
        return TradeBotStatus.TERMINATED==status;
    }

    public void terminate() {
        status = TradeBotStatus.TERMINATED;
        inactivate();
    }

    public void setStatus(TradeBotStatus status) {
        this.status = status;
        stateHistory.add(new TradeBotStateHistory(this, status));
    }

}
