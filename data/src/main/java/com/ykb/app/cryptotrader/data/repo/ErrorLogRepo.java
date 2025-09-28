package com.ykb.app.cryptotrader.data.repo;

import com.ykb.app.cryptotrader.data.model.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepo extends JpaRepository<ErrorLog, Long> {
}
