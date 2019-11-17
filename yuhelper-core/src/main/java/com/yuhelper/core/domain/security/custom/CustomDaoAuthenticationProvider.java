package com.yuhelper.core.domain.security.custom;

import com.yuhelper.core.model.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    public CustomDaoAuthenticationProvider() {
        super();
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        authentication.getDetails();
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = (User) getUserDetailsService().loadUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("Wrong login info entered.");
        }
        setPasswordEncoder(new CustomPasswordEncoder(user.getSalt()));
        if (!getPasswordEncoder().matches(password, user.getPassword()) || !user.isEnabled()) {
            throw new BadCredentialsException("Wrong login info entered.");
        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

}
