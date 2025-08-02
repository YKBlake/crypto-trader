package com.ykb.app.cryptotrader.data.domain.model

import com.ykb.app.cryptotrader.data.base.entity.BaseEntity
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class StrategySettings : BaseEntity()