package com.lhind.converter;

import com.lhind.dto.user.UserDto;
import com.lhind.dto.user.UserResponseDto;
import com.lhind.entities.User;
import com.lhind.security.UserPrincipal;

import java.util.ArrayList;
import java.util.List;

public class UserConverter {

    public static User toEntity(UserDto userDto) {
        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(userDto.getRole());
        return user;
    }

    public static UserResponseDto toDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setRole(user.getRole());
        return userResponseDto;
    }

    public static UserResponseDto toDto(UserPrincipal user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }

    public static List<UserResponseDto> toDtoList(List<User> users) {
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        users.forEach(user -> {
            userResponseDtos.add(toDto(user));
        });
        return userResponseDtos;
    }

}
