package com.ykb.app.cryptotrader.data.dao;

import com.ykb.app.cryptotrader.data.model.ErrorLog;
import com.ykb.app.cryptotrader.data.repo.ErrorLogRepo;
import org.springframework.stereotype.Component;

@Component
public final class ErrorLogDao extends Dao<ErrorLog, Long> {

    public ErrorLogDao(ErrorLogRepo repo) {
        super(repo);
    }

}
