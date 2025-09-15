package com.ykb.app.cryptotrader.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@MappedSuperclass
@Data
abstract class BaseEntity {

    @Column(name = "CREATION_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime = new Date();

    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime = null;

    @Column(name = "INACTIVATION_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inactivationTime = null;

    public void update() {
        updateTime = new Date();
    }

    public void inactivate() {
        updateTime = new Date();
        inactivationTime = updateTime;
    }

}