package com.ykb.app.cryptotrader.unittest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ykb.app.cryptotrader")
@EnableJpaRepositories(basePackages = "com.ykb.app.cryptotrader.data.repo")
@EntityScan(basePackages = "com.ykb.app.cryptotrader.data.model")
@ConfigurationPropertiesScan(basePackages = "com.ykb.app.cryptotrader")
public class UnitTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnitTestApplication.class, args);
    }

}
