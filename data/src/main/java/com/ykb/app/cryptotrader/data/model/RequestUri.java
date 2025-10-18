package com.ykb.app.cryptotrader.data.model;

import jakarta.persistence.*;
import lombok.*;
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

    public RequestUri(String uri, HttpMethod httpMethod, boolean isSecure, boolean isView) {
        id = new Key(uri, httpMethod.name());
        this.isSecure = isSecure;
        this.isView = isView;
    }

    @Embeddable
    @AllArgsConstructor
    @Data
    public static class Key implements Serializable {
        @Column(name = "URI", nullable = false)
        private String uri;

        @Column(name = "HTTP_METHOD", nullable = false)
        private String httpMethod;
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
