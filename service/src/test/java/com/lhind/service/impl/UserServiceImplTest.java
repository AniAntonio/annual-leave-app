package com.lhind.service.impl;


import com.lhind.dto.user.UserDto;
import com.lhind.entities.User;
import com.lhind.repository.AnnualLeaveRepository;
import com.lhind.repository.UserRepository;
import com.lhind.service.IAnnualLeaveService;
import com.lhind.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static com.lhind.service.impl.mock.MockObject.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private IUserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IAnnualLeaveService annualLeaveService;

    @Mock
    private AnnualLeaveRepository annualLeaveRepository;

    @Mock
    PasswordEncoder passwordEncoder = new PasswordEncoder() {
        @Override
        public String encode(CharSequence charSequence) {
            return null;
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return true;
        }
    };

    public UserServiceImplTest() {
        userService = new UserServiceImpl(passwordEncoder);
    }

    @Test
    public void insertUser() {
        UserDto userDto = userDto();
        User user = user();
        when(userRepository.userExistsWithUsername(userDto.getUsername())).thenReturn(false);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        userService.insertUser(userDto);
    }


    @Test
    public void deleteUser() {
        when(userRepository.userExistsWithId(1)).thenReturn(true);
        userService.deleteUser(1);
    }

    @Test
    public void getUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user()));
        userService.getUserById(1);
    }

    @Test
    public void updateUser() {
        when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user()));
        when(userRepository.userExistsWithEmailAndId(Mockito.anyString(), Mockito.any())).thenReturn(false);
        when(userRepository.save(Mockito.any())).thenReturn(user());
        userService.updateUser(1, userUpdateDto());
    }

    @Test
    public void getAllUsersSortedByField() {
        org.springframework.data.domain.Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "firstName"));
        final Page<User> page = new PageImpl<>(Arrays.asList(user()));
        when(userRepository.findAll(pageable)).thenReturn(page);
        userService.getAllUsersSortedByField(0, 1, "firstName");
    }
}