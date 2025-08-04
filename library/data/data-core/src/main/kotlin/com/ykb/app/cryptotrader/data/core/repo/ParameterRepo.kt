package com.ykb.app.cryptotrader.data.core.repo

import com.ykb.app.cryptotrader.data.core.model.Parameter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StrategyRepo : JpaRepository<Parameter, String>