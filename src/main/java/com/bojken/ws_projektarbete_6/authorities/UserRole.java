package com.bojken.ws_projektarbete_6.authorities;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bojken.ws_projektarbete_6.authorities.UserPermissions.*;

public enum UserRole {

    USER(GET),
    ADMIN(GET, POST, PUT, DELETE);

    private final List<String> permissions;

    UserRole(UserPermissions... permissionsList) {
        this.permissions = Arrays.stream(permissionsList)
                .map(UserPermissions::getPermission)
                .toList();
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        authorities.addAll(getPermissions().stream().map(SimpleGrantedAuthority::new).toList());

        return authorities;
    }
}