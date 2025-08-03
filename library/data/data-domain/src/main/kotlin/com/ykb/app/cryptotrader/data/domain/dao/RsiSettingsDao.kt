package com.ykb.app.cryptotrader.data.domain.dao

import com.ykb.app.cryptotrader.data.base.Dao
import com.ykb.app.cryptotrader.data.domain.model.RsiSettings
import com.ykb.app.cryptotrader.data.domain.repo.RsiSettingsRepo
import com.ykb.app.cryptotrader.utils.enums.AssetSourceType
import org.springframework.stereotype.Component

@Component
class RsiSettingsDao(repo: RsiSettingsRepo) : Dao<RsiSettings, RsiSettings.Key>(repo) {

    fun findByParams(length: Int, source: AssetSourceType, smoothingMaLength: Int): RsiSettings? {
        val list = (repo as RsiSettingsRepo).findByParams(length, source, smoothingMaLength)
        return list.stream()
            .findFirst()
            .orElse(null)
    }

}