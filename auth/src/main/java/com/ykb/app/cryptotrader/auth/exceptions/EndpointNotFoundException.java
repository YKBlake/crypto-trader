package com.ykb.app.cryptotrader.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EndpointNotFoundException extends AuthenticationException {

    public EndpointNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EndpointNotFoundException(String msg) {
        super(msg);
    }

}
