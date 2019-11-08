package org.manuel.mysportfolio.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class SystemAuthentication extends AbstractAuthenticationToken {

    public SystemAuthentication() {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}