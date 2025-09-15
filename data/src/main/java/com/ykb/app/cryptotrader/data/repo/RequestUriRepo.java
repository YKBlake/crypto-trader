package com.ykb.app.cryptotrader.data.repo;

import com.ykb.app.cryptotrader.data.model.RequestUri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestUriRepo extends JpaRepository<RequestUri, RequestUri.Key> {

    @Query("SELECT r FROM RequestUri r WHERE r.id.serviceName = :serviceName ")
    List<RequestUri> findByServiceName(String serviceName);

    @Query("SELECT r FROM RequestUri r WHERE r.id.uri = :uri AND r.id.httpMethod = :httpMethod AND r.id.serviceName = :serviceName ")
    List<RequestUri> findByUriAndMethodAndServiceName(String uri, String httpMethod, String serviceName);

}
