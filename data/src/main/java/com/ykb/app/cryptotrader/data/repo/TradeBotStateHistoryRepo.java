package com.ykb.app.cryptotrader.data.repo;

import com.ykb.app.cryptotrader.data.model.TradeBotStateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeBotStateHistoryRepo extends JpaRepository<TradeBotStateHistory, TradeBotStateHistory.Key> {
}
