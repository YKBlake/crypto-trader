package com.ykb.app.cryptotrader.data.auth.repo

import com.ykb.app.cryptotrader.data.auth.model.RequestUri
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RequestUriRepo : JpaRepository<RequestUri, RequestUri.Key> {

    @Query("SELECT r FROM RequestUri r WHERE r.id.httpMethod = :httpMethod AND r.isSecure = :isSecure ")
    fun findByMethodAndSecure(httpMethod: String, isSecure: Boolean): List<RequestUri>

    @Query("SELECT r FROM RequestUri r WHERE r.id.uri = :uri AND r.id.httpMethod = :httpMethod ")
    fun findByUriAndMethod(uri: String, httpMethod: String): List<RequestUri>

}