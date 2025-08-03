package com.ykb.app.cryptotrader.data.domain.dao

import com.ykb.app.cryptotrader.data.base.Dao
import com.ykb.app.cryptotrader.data.domain.model.MacdSettings
import com.ykb.app.cryptotrader.data.domain.repo.MacdSettingsRepo
import com.ykb.app.cryptotrader.utils.enums.AssetSourceType
import org.springframework.stereotype.Component

@Component
class MacdSettingsDao(repo: MacdSettingsRepo) : Dao<MacdSettings, MacdSettings.Key>(repo) {

    fun findByParams(shortMaLength: Int, shortMaSource: AssetSourceType, longMaLength: Int, longMaSource: AssetSourceType, signalLength: Int): MacdSettings? {
        val list = (repo as MacdSettingsRepo).findByParams(shortMaLength, shortMaSource, longMaLength, longMaSource, signalLength)
        return list.stream()
            .findFirst()
            .orElse(null)
    }

}