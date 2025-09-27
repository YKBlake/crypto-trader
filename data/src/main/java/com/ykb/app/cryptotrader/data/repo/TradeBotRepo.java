package com.ykb.app.cryptotrader.data.repo;

import com.ykb.app.cryptotrader.data.model.TradeBot;
import com.ykb.app.cryptotrader.utils.enums.TradeBotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeBotRepo extends JpaRepository<TradeBot, Long> {

    @Query("SELECT t FROM TradeBot t WHERE t.status = TradeBotStatus.TERMINATED")
    List<TradeBot> findAllNonTerminated();

}
