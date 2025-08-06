package com.ykb.app.cryptotrader.auth.service

import com.ykb.app.cryptotrader.data.auth.dao.RequestUriDao
import com.ykb.app.cryptotrader.data.auth.model.RequestUri
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service

@Service
class UriSecurityManager(
    private val dao: RequestUriDao,
    @Value($$"${app.property.auth.url.login}") private val loginUri: String,
    @Value($$"${app.properties.microservice.name}") private val serviceName: String
) {
    fun getUris(): List<RequestUri> {
        return dao.findByServiceName(serviceName)
    }

    fun doUriExist(uri: String, method: HttpMethod): Boolean {
        return dao.findByUriAndMethodAndServiceName(uri, method, serviceName).isNotEmpty()
    }

    fun isView(uri: String, method: HttpMethod): Boolean {
        val list = dao.findByUriAndMethodAndServiceName(uri, method, serviceName)
        return list.isNotEmpty() && list[0].isView()
    }

    fun isLoginView(uri: String, method: HttpMethod): Boolean {
        return loginUri==uri && HttpMethod.GET==method
    }
}