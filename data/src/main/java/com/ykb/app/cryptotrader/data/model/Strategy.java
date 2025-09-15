package com.ykb.app.cryptotrader.data.model;

import com.ykb.app.cryptotrader.utils.enums.StrategyNames;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "STRATEGY")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Strategy extends BaseIdEntity {

    @Column(name = "NAME", columnDefinition = "VARCHAR(256)", nullable = false)
    @Enumerated(EnumType.STRING)
    private StrategyNames name;

    @ManyToOne
    @JoinColumn(name = "SETTINGS_ID", nullable = false)
    private StrategySettings settings;

}
