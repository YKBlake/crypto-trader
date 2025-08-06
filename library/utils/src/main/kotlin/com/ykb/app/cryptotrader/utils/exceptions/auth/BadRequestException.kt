package com.ykb.app.cryptotrader.utils.exceptions.auth

import org.springframework.security.core.AuthenticationException

class BadRequestException(msg: String) : AuthenticationException(msg)