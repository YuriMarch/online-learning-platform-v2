package com.distancelearning.course.configs.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Getter @Setter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private UUID userId;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(UUID userId, String rolesString){
        Stream<String> rolesStream = Arrays.stream(rolesString.split(","));
        List<SimpleGrantedAuthority> authorities = rolesStream.map(SimpleGrantedAuthority::new).toList();
        return new UserDetailsImpl(
                userId,
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
