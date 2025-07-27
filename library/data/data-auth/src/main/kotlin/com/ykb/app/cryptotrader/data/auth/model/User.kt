package com.ykb.app.cryptotrader.data.auth.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "USER")
class User(
    @Column(name = "USERNAME", unique = true, columnDefinition = "VARCHAR(64)", nullable = false)
    private var username: String,
    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(256)", nullable = false)
    private var password: String,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "USER_ROLES",
        joinColumns = [JoinColumn(name = "USER_ID")],
        inverseJoinColumns = [JoinColumn(name = "ROLE_ID")]
    )
    private var roles: MutableList<Role>,
    @Column(name = "EXPIRE_DATE", unique = true, columnDefinition = "TIMESTAMP")
    private var expireDate: Date?,
    @Column(name = "LOCKED", unique = true, columnDefinition = "BOOL")
    private var locked: Boolean?,
    @Column(name = "CREDENTIALS_EXPIRE_DATE", unique = true, columnDefinition = "TIMESTAMP")
    private var credentialsExpireDate: Date?,
    @Column(name = "ENABLED", unique = true, columnDefinition = "BOOL")
    private var enabled: Boolean?,
) : UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    fun getId(): Int? {
        return id
    }

    fun setRoles(roles: List<Role>) {
        this.roles = roles.toMutableList()
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.stream()
            .flatMap { r -> r.getAuthorities().stream() }
            .distinct()
            .toList()
    }

    fun setUsername(username: String) {
        this.username = username
    }

    override fun getUsername(): String {
        return username
    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getPassword(): String {
        return password
    }

    fun expire() {
        expireDate = Date()
    }

    fun updateExpireDate(date: Date) {
        expireDate = date
    }

    override fun isAccountNonExpired(): Boolean {
        return expireDate == null || Date().before(expireDate)
    }

    fun lock() {
        locked = true
    }

    fun unlock() {
        locked = false
    }

    override fun isAccountNonLocked(): Boolean {
        return locked == null || !locked!!
    }

    fun expireCredentials() {
        credentialsExpireDate = Date()
    }

    fun updateCredentialsExpireDate(date: Date) {
        credentialsExpireDate = date
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsExpireDate == null || Date().before(credentialsExpireDate)
    }

    fun enable() {
        enabled = true
    }

    fun disable() {
        enabled = false
    }

    override fun isEnabled(): Boolean {
        return enabled == null || enabled!!
    }

}