package com.ykb.app.cryptotrader.utils.logger.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import java.util.Date

@Entity
@Table(name = "ERROR_LOG")
class ErrorLog(
    ex: Throwable,
    @Column(columnDefinition = "VARCHAR(256)", nullable = true)
    @Enumerated(EnumType.STRING)
    private val description: String?
) {

    constructor(ex: Throwable): this(ex, ex.message)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    @Column(name = "CREATION_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private val creationTime = Date()

    @Column(columnDefinition = "TEXT")
    private val stacktrace: String = ex.stackTraceToString()
}