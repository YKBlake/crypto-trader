package com.ykb.app.cryptotrader.data.repo;

import com.ykb.app.cryptotrader.data.model.StrategySettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategySettingsRepo extends JpaRepository<StrategySettings, StrategySettings.Key> {
}
