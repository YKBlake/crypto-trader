package com.ykb.app.cryptotrader.utils.exceptions

import org.springframework.security.core.AuthenticationException

class NotFoundException(msg: String) : AuthenticationException(msg)