package com.exampleForVano.VanoVpered.security;

import com.exampleForVano.VanoVpered.model.Role;
import org.springframework.security.core.GrantedAuthority;


public class GrantedAuthorityImp implements GrantedAuthority {
    private final Role role;

    public GrantedAuthorityImp(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getName();
    }

    @Override
    public String toString() {
        return role.getName();
    }
}
