package com.ykb.app.cryptotrader.data.auth.repo

import com.ykb.app.cryptotrader.data.auth.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo : JpaRepository<User, Int>