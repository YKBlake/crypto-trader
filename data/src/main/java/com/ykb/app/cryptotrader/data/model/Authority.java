package com.ykb.app.cryptotrader.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "AUTHORITY")
@Data
@EqualsAndHashCode(callSuper = true)
public class Authority extends BaseEntity implements GrantedAuthority {

    @Id
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

}
