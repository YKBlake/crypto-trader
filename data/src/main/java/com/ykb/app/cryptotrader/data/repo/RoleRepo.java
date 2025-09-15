package com.ykb.app.cryptotrader.data.repo;

import com.ykb.app.cryptotrader.data.model.Role;
import com.ykb.app.cryptotrader.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, String> {
}
