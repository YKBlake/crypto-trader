package com.ykb.app.cryptotrader.data.auth.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "AUTHORITY")
class Authority(
    @Id
    private val authority: String
) : GrantedAuthority {

    override fun getAuthority(): String {
        return authority
    }

}