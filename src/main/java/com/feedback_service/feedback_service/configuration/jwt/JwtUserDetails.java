package com.feedback_service.feedback_service.configuration.jwt;

import com.feedback_service.feedback_service.entity.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUserDetails implements UserDetails {
    private UserEntity user;
    Collection<GrantedAuthority> authorities;

    public JwtUserDetails(UserEntity user, Collection<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    private final String username = user.getUsername();
    private final String password = user.getPassword();
    private final Boolean isAccountNonLocked = !user.isAccountLocked();
    private final String securityStamp = user.getSecurityStamp();
    private final Collection<GrantedAuthority> grantedAuthorities = authorities;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
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