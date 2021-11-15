package com.lhind.service.impl;

import com.lhind.converter.UserConverter;
import com.lhind.dto.user.UpdatePasswordDto;
import com.lhind.dto.user.UserDto;
import com.lhind.dto.user.UserResponseDto;
import com.lhind.dto.user.UserUpdateDto;
import com.lhind.entities.User;
import com.lhind.enums.Role;
import com.lhind.exception.InputException;
import com.lhind.exception.LhindNotFoundException;
import com.lhind.repository.AnnualLeaveRepository;
import com.lhind.repository.UserRepository;
import com.lhind.service.IAnnualLeaveService;
import com.lhind.service.IUserService;
import com.lhind.util.BadRequest;
import com.lhind.util.NoData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnnualLeaveRepository annualLeaveRepository;

    @Autowired
    private IAnnualLeaveService annualLeaveService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserResponseDto insertUser(UserDto userDto) {
        if (!userRepository.userExistsWithUsername(userDto.getUsername())) {
            log.info("Checked if user exists before inserting to DB!");
            User user = UserConverter.toEntity(userDto);
            user.setFlagDeleted(Boolean.FALSE);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            UserResponseDto responseDto = UserConverter.toDto(userRepository.save(user));
            log.info("User created");
            if (user.getRole().name().equals(Role.USER.name())) {
                annualLeaveService.insertAnnualLeave(userDto, user);
            }
            return responseDto;
        } else {
            throw new InputException(BadRequest.USER_EXISTS);
        }
    }

    @Override
    public void changePassword(UpdatePasswordDto updatePasswordDto, Integer userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new LhindNotFoundException(NoData.USER_NOT_FOUND));
        log.info("Checking if old password given matches!");
        if (!passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword())) {
            throw new InputException(BadRequest.OLD_PASS_NOT_MATCH);
        }
        log.info("Checking if the 2 passwords are not the same!");
        if (passwordEncoder.matches(updatePasswordDto.getNewPassword(), user.getPassword())) {
            throw new InputException(BadRequest.PASSWORD_SAME_AS_OLD);
        }
        user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        if (userRepository.userExistsWithId(id)) {
            log.info("Setting flag deleted to true for the user!");
            userRepository.delete(id);
        } else {
            throw new LhindNotFoundException(NoData.USER_NOT_FOUND);
        }
    }


    @Override
    public UserResponseDto getUserById(Integer id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new LhindNotFoundException(NoData.USER_NOT_FOUND));
        UserResponseDto userResponseDto = UserConverter.toDto(user);
        return userResponseDto;
    }

    @Override
    public UserResponseDto updateUser(Integer id, UserUpdateDto userUpdateDtoDto) {
        log.info("Updating user");
        User user = userRepository.findById(id).
                orElseThrow(() -> new LhindNotFoundException(NoData.USER_NOT_FOUND));
        log.info("Checking input email is the same as the one in DB!");
        if (userRepository.userExistsWithEmailAndId(userUpdateDtoDto.getEmail(), id)) {
            throw new InputException(BadRequest.USER_EXISTS);
        }
        user.setFirstName(userUpdateDtoDto.getFirstName());
        user.setLastName(userUpdateDtoDto.getLastName());
        user.setEmail(userUpdateDtoDto.getEmail());
        UserResponseDto responseDto = UserConverter.toDto(userRepository.save(user));
        log.info("User updated successfully");
        return responseDto;
    }

    @Override
    public Page<UserResponseDto> getAllUsersSortedByField(Integer pageNumber, Integer pageSize, String field) {
        log.info("Getting all users sorted!");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, field));
        List<UserResponseDto> userResponseDtoList = UserConverter.toDtoList(userRepository.findAll(pageable).getContent());
        return new PageImpl<>(userResponseDtoList);
    }
}
