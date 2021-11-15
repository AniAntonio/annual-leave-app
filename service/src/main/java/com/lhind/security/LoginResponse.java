package com.lhind.security;

import com.lhind.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Role role;
    private String token;
}
