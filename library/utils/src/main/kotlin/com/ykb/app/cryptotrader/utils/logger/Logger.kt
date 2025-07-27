package com.ykb.app.cryptotrader.utils.logger

import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

class Logger(
    clazz: KClass<*>
) {
    companion object {
        fun getLogger(clazz: KClass<*>): Logger {
            return Logger(clazz)
        }
    }

    private val log = LoggerFactory.getLogger(clazz.java)

    fun info(str: String) {
        log.info(str)
    }

    fun error(str: String) {
        log.error(str)
    }

    fun error(str: String, ex: Throwable) {
        log.error(str, ex)
    }

    fun error(ex: Throwable) {
        log.error(ex.message, ex)
    }

}