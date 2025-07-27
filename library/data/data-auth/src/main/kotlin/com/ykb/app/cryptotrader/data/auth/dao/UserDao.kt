package com.ykb.app.cryptotrader.data.auth.dao

import com.ykb.app.cryptotrader.data.auth.model.User
import com.ykb.app.cryptotrader.data.auth.repo.UserRepo
import com.ykb.app.cryptotrader.data.base.Dao
import org.springframework.stereotype.Service

@Service
class UserDao(repo: UserRepo) : Dao<User, Int>(repo)