package com.ykb.app.cryptotrader.data.dao;

import com.ykb.app.cryptotrader.data.model.StrategySettings;
import com.ykb.app.cryptotrader.data.repo.StrategySettingsRepo;
import org.springframework.stereotype.Component;

@Component
public final class StrategySettingsDao extends Dao<StrategySettings, StrategySettings.Key> {

    public StrategySettingsDao(StrategySettingsRepo repo) {
        super(repo);
    }

}
