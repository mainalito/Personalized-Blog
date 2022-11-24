package com.blog.security;

import com.blog.entities.RegisterUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class SecurityUser implements UserDetails {
    private final RegisterUser loginUser; // acts as a wrapper of entity loginUser


    @Override
    public String getPassword() {
        return loginUser.getPassword();
    }

    @Override
    public String getUsername() {
        return loginUser.getUsername();
    }

    /* GRANTED AUTHORITIES HAS
         1. AUTHORITIES - ACTION (VERB) RWX
        2. ROLES - SUBJECT (ADMIN, MANAGER, CLIENT)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         // gives privileges
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(loginUser.getRole().name());
//        return List.of(()->"read");
        return Collections.singleton(authority);
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
