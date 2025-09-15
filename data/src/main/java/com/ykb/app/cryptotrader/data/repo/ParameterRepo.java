package com.ykb.app.cryptotrader.data.repo;

import com.ykb.app.cryptotrader.data.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepo extends JpaRepository<Parameter, String> {
}
