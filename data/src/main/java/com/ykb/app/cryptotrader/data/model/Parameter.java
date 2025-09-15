package com.ykb.app.cryptotrader.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "PARAMETER")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Parameter extends BaseEntity {

    @Id
    @Column(name = "KEY", columnDefinition = "VARCHAR(256)")
    private String key;

    @Column(name = "VALUE", columnDefinition = "VARCHAR(512)")
    private String value;

    @Column(name = "TYPE", columnDefinition = "VARCHAR(32)")
    @Enumerated(EnumType.STRING)
    private ParameterType type;

    public enum ParameterType {
        SYSTEM_CONFIG, DOMAIN_VALUE
    }

}
