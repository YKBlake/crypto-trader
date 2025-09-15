package com.ykb.app.cryptotrader.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROLE")
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    @Id
    @Column(name = "NAME", nullable = false, columnDefinition = "VARCHAR(64)")
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ROLE_AUTHORITIES",
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID")
    )
    private List<Authority> authorities;

    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> dummyAuths = new ArrayList<>(authorities);
        dummyAuths.add(new SimpleGrantedAuthority("ROLE_" + name));
        return dummyAuths;
    }

}