package com.ykb.app.cryptotrader.data.domain.repo

import com.ykb.app.cryptotrader.data.domain.model.MacdSettings
import com.ykb.app.cryptotrader.utils.enums.AssetSourceType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MacdSettingsRepo : JpaRepository<MacdSettings, MacdSettings.Key> {

    @Query("SELECT a FROM MacdSettings a WHERE a.key.shortMaLength = :shortMaLength AND a.key.shortMaSource = :shortMaSource AND a.key.longMaLength = :longMaLength AND a.key.longMaSource = :longMaSource AND a.key.signalLength = :signalLength")
    fun findByParams(shortMaLength: Int, shortMaSource: AssetSourceType, longMaLength: Int, longMaSource: AssetSourceType, signalLength: Int): List<MacdSettings>

}