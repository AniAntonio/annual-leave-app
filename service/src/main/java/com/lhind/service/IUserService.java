package com.lhind.service;

import com.lhind.dto.user.UpdatePasswordDto;
import com.lhind.dto.user.UserDto;
import com.lhind.dto.user.UserResponseDto;
import com.lhind.dto.user.UserUpdateDto;
import org.springframework.data.domain.Page;

public interface IUserService {

    UserResponseDto insertUser(UserDto userDto);

    void changePassword(UpdatePasswordDto updatePasswordDto, Integer userId);

    void deleteUser(Integer id);

    UserResponseDto getUserById(Integer id);

    UserResponseDto updateUser(Integer id, UserUpdateDto userUpdateDtoDto);

    Page<UserResponseDto> getAllUsersSortedByField(Integer pageNumber, Integer pageSize, String field);
}
