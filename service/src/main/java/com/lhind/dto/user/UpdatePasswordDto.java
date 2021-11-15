package com.lhind.dto.user;

import com.lhind.util.RegexPatterns;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdatePasswordDto {
    @NotBlank
    String oldPassword;

    @Pattern(regexp = RegexPatterns.PASSWORD_REGEX)
    String newPassword;
}
