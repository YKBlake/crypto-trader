package com.ykb.app.cryptotrader.data.domain.repo

import com.ykb.app.cryptotrader.data.domain.model.RsiSettings
import com.ykb.app.cryptotrader.utils.enums.AssetSourceType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RsiSettingsRepo : JpaRepository<RsiSettings, RsiSettings.Key> {

    @Query("SELECT a FROM RsiSettings a WHERE a.key.length = :length AND a.key.source = :source AND a.key.smoothingMaLength = :smoothingMaLength")
    fun findByParams(length: Int, source: AssetSourceType, smoothingMaLength: Int): List<RsiSettings>

}