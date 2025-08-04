package com.ykb.app.cryptotrader.data.auth.dao

import com.ykb.app.cryptotrader.data.auth.model.RequestUri
import com.ykb.app.cryptotrader.data.auth.repo.RequestUriRepo
import com.ykb.app.cryptotrader.data.base.Dao
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service

@Service
class RequestUriDao(repo: RequestUriRepo) : Dao<RequestUri, RequestUri.Key>(repo) {

    fun findByServiceName(serviceName: String): List<RequestUri> {
        return (repo as RequestUriRepo).findByServiceName(serviceName)
    }

    fun findByUriAndMethodAndServiceName(uri: String, httpMethod: HttpMethod, serviceName: String): List<RequestUri> {
        return (repo as RequestUriRepo).findByUriAndMethodAndServiceName(uri, httpMethod.name(), serviceName)
    }

}