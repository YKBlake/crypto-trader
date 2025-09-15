package com.ykb.app.cryptotrader.data.dao;

import com.ykb.app.cryptotrader.data.model.Parameter;
import com.ykb.app.cryptotrader.data.repo.ParameterRepo;
import org.springframework.stereotype.Component;

@Component
public final class ParameterDao extends Dao<Parameter, String> {

    public ParameterDao(ParameterRepo repo) {
        super(repo);
    }

}
