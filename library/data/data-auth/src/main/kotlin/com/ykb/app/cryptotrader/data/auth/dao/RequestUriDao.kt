package com.ykb.app.cryptotrader.data.auth.dao

import com.ykb.app.cryptotrader.data.auth.model.RequestUri
import com.ykb.app.cryptotrader.data.auth.repo.RequestUriRepo
import com.ykb.app.cryptotrader.data.base.Dao
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service

@Service
class RequestUriDao(repo: RequestUriRepo) : Dao<RequestUri, RequestUri.Key>(repo) {
    fun findByMethodAndSecure(httpMethod: HttpMethod, isSecure: Boolean): List<RequestUri> {
        return (repo as RequestUriRepo).findByMethodAndSecure(httpMethod.name(), isSecure)
    }

    fun findByUriAndMethod(uri: String, httpMethod: HttpMethod): List<RequestUri> {
        return (repo as RequestUriRepo).findByUriAndMethod(uri, httpMethod.name())
    }
}