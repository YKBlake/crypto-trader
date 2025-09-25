package com.ykb.app.cryptotrader.data.dao;

import com.ykb.app.cryptotrader.data.model.User;
import com.ykb.app.cryptotrader.data.repo.UserRepo;
import org.springframework.stereotype.Component;

@Component
public final class UserDao extends Dao<User, Long> {

    public UserDao(UserRepo repo) {
        super(repo);
    }

    public boolean usernameExists(String username) {
        return ((UserRepo) repo).usernameExists(username);
    }

}
