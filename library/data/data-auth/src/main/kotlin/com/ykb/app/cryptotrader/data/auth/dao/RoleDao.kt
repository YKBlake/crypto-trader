package com.ykb.app.cryptotrader.data.auth.dao

import com.ykb.app.cryptotrader.data.auth.model.Role
import com.ykb.app.cryptotrader.data.auth.repo.RoleRepo
import com.ykb.app.cryptotrader.data.base.Dao
import org.springframework.stereotype.Service

@Service
class RoleDao(repo: RoleRepo) : Dao<Role, String>(repo)