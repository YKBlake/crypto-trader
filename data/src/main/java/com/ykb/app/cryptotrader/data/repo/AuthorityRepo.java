package com.ykb.app.cryptotrader.data.repo;

import com.ykb.app.cryptotrader.data.model.Authority;
import com.ykb.app.cryptotrader.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, String> {
}
