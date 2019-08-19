package com.rezorl.jwt.springbootjwtsecurityjpaliquibase.service;

import javax.servlet.http.HttpServletRequest;
import com.rezorl.jwt.springbootjwtsecurityjpaliquibase.exception.CustomException;
import com.rezorl.jwt.springbootjwtsecurityjpaliquibase.model.AppUser;
import com.rezorl.jwt.springbootjwtsecurityjpaliquibase.repository.UserRepository;
import com.rezorl.jwt.springbootjwtsecurityjpaliquibase.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(AppUser user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    public AppUser search(String username) {
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public AppUser whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final AppUser user = userRepository.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + s + "' not found");
        }

        return user;
    }
}
