package com.ykb.app.cryptotrader.utils.exceptions

class InvalidMsClientKeyException(msg: String?) : RuntimeException(msg) {

    constructor() : this(null)

}