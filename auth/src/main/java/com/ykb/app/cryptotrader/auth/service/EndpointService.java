package com.ykb.app.cryptotrader.auth.service;

import com.ykb.app.cryptotrader.data.dao.RequestUriDao;
import com.ykb.app.cryptotrader.data.model.RequestUri;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class EndpointService {

    private final Set<RequestUri> requestUris;
    private final UriMatcher uriMatcher = (uri1, uri2, method1, method2) -> {
        if(!method1.equals(method2))
            return false;

        if(uri1.equals(uri2))
            return true;

        int i = uri1.indexOf("**");
        return i != -1 && uri1.substring(0, i).equals(uri2);
    };

    public EndpointService(RequestUriDao dao) {
        requestUris = new TreeSet<>(dao.findAll());
    }

    public Set<RequestUri> get() {
        return requestUris;
    }

    public boolean exists(String uri, HttpMethod method) {
        return requestUris.stream().anyMatch(ruri -> uriMatcher.match(ruri.getUri(), uri, ruri.getHttpMethod(), method));
    }

    public boolean isView(String uri, HttpMethod method) {
        return requestUris.stream().anyMatch(ruri -> uriMatcher.match(ruri.getUri(), uri, ruri.getHttpMethod(), method) && ruri.isView());
    }

    public boolean isAuthenticated(String uri, HttpMethod method) {
        return requestUris.stream().anyMatch(ruri -> uriMatcher.match(ruri.getUri(), uri, ruri.getHttpMethod(), method) && ruri.isSecure());
    }

    @FunctionalInterface
    private interface UriMatcher {
        boolean match(String uri1, String uri2, HttpMethod method1, HttpMethod method2);
    }

}
