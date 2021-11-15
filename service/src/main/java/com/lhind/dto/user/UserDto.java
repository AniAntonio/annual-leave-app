package com.lhind.dto.user;

import com.lhind.enums.Role;
import com.lhind.util.RegexPatterns;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserDto {

    @Pattern(regexp = RegexPatterns.FORMAT, message = "Wrong name format")
    private String firstName;

    @Pattern(regexp = RegexPatterns.FORMAT, message = "Wrong surname format")
    private String lastName;

    @Pattern(regexp = RegexPatterns.FORMAT, message = "Wrong name format")
    private String username;

    @Pattern(regexp = RegexPatterns.PASSWORD_REGEX,
            message = "Password must be between 6 - 16 characters, one upper case character, have numbers and a special character")
    private String password;

    @Pattern(regexp = RegexPatterns.EMAIL_REGEX, message = "Wrong email format")
    private String email;

    private Integer supervisorId;

    private Role role;

}
