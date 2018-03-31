package security;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        System.out.println(auth.getName());

        User user = userService.findByUsername(username);

        if(user != null && user.getPassword().equals(password))
        {
            List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
            authority.add(new SimpleGrantedAuthority("ROLE_" + "USER"));
            if(user.isAdmin())
                authority.add(new SimpleGrantedAuthority("ROLE_" + "ADMIN"));
            return new UsernamePasswordAuthenticationToken
                    (username, password, authority);
        } else {
            throw new
                    BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return (UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(auth));
    }
}