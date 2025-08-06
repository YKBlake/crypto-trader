package com.ykb.app.cryptotrader.utils.exceptions.auth

import org.springframework.security.core.AuthenticationException

class InternalServerErrorException(msg: String) : AuthenticationException(msg)