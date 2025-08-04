package com.ykb.app.cryptotrader.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["com.ykb.app.cryptotrader"])
@EnableFeignClients
@EnableJpaRepositories(basePackages = ["com.ykb.app.cryptotrader.data.auth.repo","com.ykb.app.cryptotrader.data.domain.repo"])
class WebApplication

fun main(args: Array<String>) {
    runApplication<WebApplication>(*args)
}