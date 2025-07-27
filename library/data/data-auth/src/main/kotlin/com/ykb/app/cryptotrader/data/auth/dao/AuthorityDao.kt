package com.ykb.app.cryptotrader.data.auth.dao

import com.ykb.app.cryptotrader.data.auth.model.Authority
import com.ykb.app.cryptotrader.data.auth.repo.AuthorityRepo
import com.ykb.app.cryptotrader.data.base.Dao
import org.springframework.stereotype.Service

@Service
class AuthorityDao(repo: AuthorityRepo) : Dao<Authority, String>(repo)