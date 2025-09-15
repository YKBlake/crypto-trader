package com.ykb.app.cryptotrader.data.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "USER")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseIdEntity implements UserDetails {

    @Column(name = "USERNAME", unique = true, columnDefinition = "VARCHAR(64)", nullable = false)
    private String username;

    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(256)", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private List<Role> roles = new ArrayList<>();

    @Column(name = "EXPIRE_DATE", unique = true, columnDefinition = "TIMESTAMP")
    private Date expireDate;

    @Column(name = "LOCKED", unique = true, columnDefinition = "BOOL")
    private Boolean locked;

    @Column(name = "CREDENTIALS_EXPIRE_DATE", unique = true, columnDefinition = "TIMESTAMP")
    private Date credentialsExpireDate;

    @Column(name = "ENABLED", unique = true, columnDefinition = "BOOL")
    private Boolean enabled;

    protected User() { } // JPA

    public User(String username,
                String password,
                List<Role> roles,
                Date expireDate,
                Boolean locked,
                Date credentialsExpireDate,
                Boolean enabled) {
        this.username = username;
        this.password = password;
        if (roles != null) this.roles = new ArrayList<>(roles);
        this.expireDate = expireDate;
        this.locked = locked;
        this.credentialsExpireDate = credentialsExpireDate;
        this.enabled = enabled;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles != null ? new ArrayList<>(roles) : new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(r -> r.getAuthorities().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public void setUsername(String username) { this.username = username; }

    @Override
    public String getUsername() { return username; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String getPassword() { return password; }

    public void expire() { this.expireDate = new Date(); }

    public void updateExpireDate(Date date) { this.expireDate = date; }

    @Override
    public boolean isAccountNonExpired() {
        return expireDate == null || new Date().before(expireDate);
    }

    public void lock() { this.locked = true; }

    public void unlock() { this.locked = false; }

    @Override
    public boolean isAccountNonLocked() {
        return locked == null || !locked;
    }

    public void expireCredentials() { this.credentialsExpireDate = new Date(); }

    public void updateCredentialsExpireDate(Date date) { this.credentialsExpireDate = date; }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsExpireDate == null || new Date().before(credentialsExpireDate);
    }

    public void enable() { this.enabled = true; }

    public void disable() { this.enabled = false; }

    @Override
    public boolean isEnabled() {
        return enabled == null || enabled;
    }
}