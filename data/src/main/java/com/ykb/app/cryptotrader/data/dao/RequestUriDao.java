package com.ykb.app.cryptotrader.data.dao;

import com.ykb.app.cryptotrader.data.model.RequestUri;
import com.ykb.app.cryptotrader.data.repo.RequestUriRepo;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class RequestUriDao extends Dao<RequestUri, RequestUri.Key> {

    public RequestUriDao(RequestUriRepo repo) {
        super(repo);
    }

    public List<RequestUri> findByUriAndMethodAndServiceName(String uri, HttpMethod httpMethod) {
        return ((RequestUriRepo) repo).findByUriAndMethodAndServiceName(uri, httpMethod.name());
    }

}
