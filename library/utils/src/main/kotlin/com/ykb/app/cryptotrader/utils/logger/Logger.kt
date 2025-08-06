package com.ykb.app.cryptotrader.utils.logger

import com.ykb.app.cryptotrader.utils.logger.data.ErrorLog
import com.ykb.app.cryptotrader.utils.logger.data.ErrorLogRepo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import kotlin.reflect.KClass

class Logger(
    clazz: KClass<*>
) {

    @Autowired
    private lateinit var errorLogRepo: ErrorLogRepo

    companion object {
        fun getLogger(clazz: KClass<*>): Logger {
            return Logger(clazz)
        }
    }

    private val log = LoggerFactory.getLogger(clazz.java)

    fun info(msg: String) {
        log.info(msg)
    }

    fun error(msg: String) {
        log.error(msg)
    }

    fun error(msg: String, t: Throwable) {
        log.error(msg, t)
        errorLogRepo.save(ErrorLog(t, msg))
    }

    fun error(t: Throwable) {
        log.error(t.message, t)
        errorLogRepo.save(ErrorLog(t))
    }

}