package com.ykb.app.cryptotrader.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.PrintWriter;
import java.io.StringWriter;

@Entity
@Table(name = "ERROR_LOG")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ErrorLog extends BaseIdEntity {

    @Column(columnDefinition = "VARCHAR(2048)", nullable = false)
    private String message;

    @Lob
    @Column(name = "STACK_TRACE")
    private String stackTrace;

    public ErrorLog(String msg) {
        this(msg, null);
    }

    public ErrorLog(Exception e) {
        this(null, e);
    }

    public ErrorLog(String msg, Exception e) {
        message = msg == null ? e.getMessage() : msg;
        stackTrace = getStackTraceAsString(e);
    }

    private String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}
