package com.lhind.security;


import com.lhind.enums.Role;
import com.lhind.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthenticationService {

    @Autowired
    private JwtProvider jwtProvider;

    public LoginResponse createResponse(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateJwtToken(authentication);
        UserPrincipal userPrinciple = (UserPrincipal) authentication.getPrincipal();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(userPrinciple.getId());
        loginResponse.setToken(token);
        loginResponse.setEmail(userPrinciple.getEmail());
        loginResponse.setFirstName(userPrinciple.getName());
        loginResponse.setUsername(userPrinciple.getUsername());
        loginResponse.setLastName(userPrinciple.getLastName());
        loginResponse.setRole(Role.valueOf(userPrinciple.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0)));
        return loginResponse;
    }
}