package com.ykb.app.cryptotrader.cloud

import org.springframework.beans.factory.annotation.Value

abstract class BaseMsController {

    @Value($$"${app.properties.microservice.key}")
    private lateinit var key: String

    protected fun validateKey(key: String) {
        if(this.key!=key)
            throw IllegalArgumentException("The microservice communication key [$key] is not valid")
    }

}