package com.lhind.security.jwt;


import com.lhind.entities.User;
import com.lhind.exception.LhindNotFoundException;
import com.lhind.repository.UserRepository;
import com.lhind.security.UserPrincipal;
import com.lhind.util.NoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new LhindNotFoundException(NoData.USER_NOT_FOUND);
        }
        return UserPrincipal.build(optionalUser.get());
    }
}
