package com.feedback_service.feedback_service.configuration.jwt;

import com.feedback_service.feedback_service.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class JwtUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Boolean isAccountNonLocked;
    private final String securityStamp;

    public JwtUserDetails(UserEntity newUser) {
        this.username = newUser.getUsername();
        this.password = newUser.getPassword();
        this.isAccountNonLocked = !newUser.isAccountLocked();
        this.securityStamp = newUser.getSecurityStamp();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public String getSecurityStamp() {
        return securityStamp;
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