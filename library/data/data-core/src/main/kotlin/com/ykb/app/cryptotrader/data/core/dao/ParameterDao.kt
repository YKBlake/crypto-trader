package com.ykb.app.cryptotrader.data.core.dao

import com.ykb.app.cryptotrader.data.base.Dao
import com.ykb.app.cryptotrader.data.core.model.Parameter
import com.ykb.app.cryptotrader.data.core.repo.StrategyRepo
import org.springframework.stereotype.Component

@Component
class ParameterDao(repo: StrategyRepo) : Dao<Parameter, String>(repo)