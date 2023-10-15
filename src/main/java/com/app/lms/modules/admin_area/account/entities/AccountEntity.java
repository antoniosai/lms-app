package com.app.lms.modules.admin_area.account.entities;

import com.app.lms.enums.AccountTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "m_account")
public class AccountEntity implements UserDetails {
    @Id
    @Column(name = "account_uuid", unique = true, nullable = false)
    private UUID accountUuid;

    @Column(name = "account_username", nullable = false, unique = true, length = 20)
    private String accountUsername;

    @Column(name = "account_password", nullable = false)
    private String accountPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 20)
    private AccountTypeEnum accountType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(accountType.name()));
    }

    @Override
    public String getPassword() {
        return accountPassword;
    }

    @Override
    public String getUsername() {
        return accountUsername;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
