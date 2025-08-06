package com.ykb.app.cryptotrader.utils.logger.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ErrorLogRepo : JpaRepository<ErrorLog, Int>