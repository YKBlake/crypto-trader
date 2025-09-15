package com.ykb.app.cryptotrader.data.dao;

import com.ykb.app.cryptotrader.data.model.Authority;
import com.ykb.app.cryptotrader.data.repo.AuthorityRepo;
import org.springframework.stereotype.Component;

@Component
public final class AuthorityDao extends Dao<Authority, String> {

    public AuthorityDao(AuthorityRepo repo) {
        super(repo);
    }

}
