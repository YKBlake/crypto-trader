package com.ykb.app.cryptotrader.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Entity
@Table(name = "STRATEGY_SETTINGS")
@EqualsAndHashCode(callSuper = true)
public class StrategySettings extends BaseEntity {

    @Id
    private final Key id;

    public StrategySettings(String key, String value) {
        id = new Key(key, value);
    }

    public String getKey() {
        return id.key;
    }

    public void setKey(String key) {
        this.id.key = key;
    }

    public String getValue() {
        return id.value;
    }

    public void setValue(String value) {
        this.id.value = value;
    }

    @Embeddable
    @AllArgsConstructor
    @Data
    public static class Key implements Serializable {
        @Column(name = "KEY", columnDefinition = "VARCHAR(128)", nullable = false)
        private String key;

        @Column(name = "VALUE", columnDefinition = "VARCHAR(512)", nullable = false)
        private String value;
    }

}