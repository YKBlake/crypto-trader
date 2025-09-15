package com.ykb.app.cryptotrader.data.dao;

import com.ykb.app.cryptotrader.data.model.Role;
import com.ykb.app.cryptotrader.data.repo.RoleRepo;
import org.springframework.stereotype.Component;

@Component
public final class RoleDao extends Dao<Role, String> {

    public RoleDao(RoleRepo repo) {
        super(repo);
    }

}
