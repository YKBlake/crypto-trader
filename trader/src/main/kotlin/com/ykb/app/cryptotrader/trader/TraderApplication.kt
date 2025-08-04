package com.ykb.app.cryptotrader.trader

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["com.ykb.app.cryptotrader"])
//@EnableJpaRepositories(basePackages = ["com.ykb.app.cryptotrader.data.auth.repo","com.ykb.app.cryptotrader.data.domain.repo"])
open class TraderApplication

fun main(args: Array<String>) {
    runApplication<TraderApplication>(*args)
}