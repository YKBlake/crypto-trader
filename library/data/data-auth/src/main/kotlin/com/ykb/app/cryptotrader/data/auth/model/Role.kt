package com.ykb.app.cryptotrader.data.auth.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Entity
@Table(name = "ROLE")
class Role(
    @Id
    @Column(name = "NAME", nullable = false, columnDefinition = "VARCHAR(64)")
    private val name: String,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "ROLE_AUTHORITIES",
        joinColumns = [JoinColumn(name = "ROLE_ID")],
        inverseJoinColumns = [JoinColumn(name = "AUTHORITY_ID")]
    )
    private val authorities: MutableList<Authority>
) {

    fun getName(): String {
        return name
    }

    fun getAuthorities(): List<GrantedAuthority> {
        val dummyAuths: MutableList<GrantedAuthority> = authorities.toMutableList()
        dummyAuths.add(SimpleGrantedAuthority("ROLE_$name"))
        return dummyAuths
    }

}