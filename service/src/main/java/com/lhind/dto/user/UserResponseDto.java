package com.lhind.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lhind.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    @JsonIgnore
    private String password;

    private Role role;
}
