package com.ykb.app.cryptotrader.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.io.Serializable;

@Entity
@Table(name = "REQUEST_URI")
@EqualsAndHashCode(callSuper = true)
public class RequestUri extends BaseEntity {

    @EmbeddedId
    private final Key id;

    @Column(name = "IS_SECURE", nullable = false)
    @Getter
    @Setter
    private boolean isSecure;

    @Column(name = "IS_VIEW", nullable = false)
    @Getter
    @Setter
    private boolean isView;

    public RequestUri(String serviceName, String uri, HttpMethod httpMethod, boolean isSecure, boolean isView) {
        id = new Key(serviceName, uri, httpMethod.name());
        this.isSecure = isSecure;
        this.isView = isView;
    }

    @Embeddable
    @AllArgsConstructor
    public static class Key implements Serializable {
        @Column(name = "SERVICE_NAME", nullable = false)
        String serviceName;

        @Column(name = "URI", nullable = false)
        String uri;

        @Column(name = "HTTP_METHOD", nullable = false)
        String httpMethod;
    }

    public void setServiceName(String serviceName) {
        id.serviceName = serviceName;
    }

    public String getServiceName() {
        return id.serviceName;
    }

    public void setUri(String uri) {
        id.uri = uri;
    }

    public String getUri() {
        return id.uri;
    }

    public void setServiceName(HttpMethod httpMethod) {
        id.httpMethod = httpMethod.name();
    }

    public HttpMethod getHttpMethod() {
        return HttpMethod.valueOf(id.httpMethod);
    }

}
