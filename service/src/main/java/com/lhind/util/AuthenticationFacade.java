package com.lhind.util;

import com.lhind.converter.UserConverter;
import com.lhind.dto.user.UserResponseDto;
import com.lhind.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UserPrincipal getUserPrincipal() {
        return (UserPrincipal) getAuthentication().getPrincipal();
    }

    public UserResponseDto getUserDto() {
        return UserConverter.toDto(getUserPrincipal());
    }
}
