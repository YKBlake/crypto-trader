package com.ykb.app.cryptotrader.data.auth.model

import jakarta.persistence.*
import org.springframework.http.HttpMethod
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "REQUEST_URI")
class RequestUri(
    uri: String,
    httpMethod: HttpMethod,

    @Column(name = "IS_SECURE", nullable = false)
    private val isSecure: Boolean,

    @Column(name = "IS_VIEW", nullable = false)
    private val isView: Boolean
) {

    @EmbeddedId
    private val id: Key = Key(uri, httpMethod.name())

    @Embeddable
    data class Key(
        @Column(name = "URI", nullable = false)
        val uri: String,
        @Column(name = "HTTP_METHOD", nullable = false)
        val httpMethod: String
    ): Serializable

    fun getUri(): String {
        return id.uri
    }

    fun getHttpMethod(): HttpMethod {
        return HttpMethod.valueOf(id.httpMethod)
    }

    fun isSecure(): Boolean {
        return isSecure
    }

    fun isView(): Boolean {
        return isView
    }

    override fun equals(other: Any?): Boolean {
        return other is RequestUri &&
                other.getUri() == getUri() &&
                other.getHttpMethod() == getHttpMethod() &&
                other.isSecure() == isSecure() &&
                other.isView() == isView()
    }

    override fun hashCode(): Int {
        return Objects.hash(getUri(), getHttpMethod(), isSecure(), isView())
    }

}