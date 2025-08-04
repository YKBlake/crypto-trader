package com.ykb.app.cryptotrader.domaindto.base

abstract class Dto(
    protected val fault: FaultDto?
) {

    constructor(): this(null)

}