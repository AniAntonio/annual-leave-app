package com.lhind.service.impl;

import com.lhind.dto.user.UserDto;
import com.lhind.entities.AnnualLeave;
import com.lhind.entities.User;
import com.lhind.enums.Role;
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

import java.util.Optional;

import static com.lhind.service.impl.mock.MockObject.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnnualLeaveServiceImplTest {

    @InjectMocks
    private IAnnualLeaveService annualLeaveService= new AnnualLeaveServiceImpl();

    @Mock
    private UserRepository userRepository;

    @Mock
    private AnnualLeaveRepository annualLeaveRepository;

    @Test
    public void getAnnualLeaveByUserId() {
        when(annualLeaveRepository.findByUserId(1)).thenReturn(Optional.of(new AnnualLeave()));
        annualLeaveService.getAnnualLeaveByUserId(1);
    }

    @Test
    public void insertAnnualLeave() {
        User user = user();
        user.setRole(Role.SUPERVISOR);
        UserDto userDto = userDto();
        when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        when(annualLeaveRepository.save(Mockito.any(AnnualLeave.class))).thenReturn(new AnnualLeave());
        annualLeaveService.insertAnnualLeave(userDto,user);
    }

    @Test
    public void updateAnnualLeave() {
        when(annualLeaveRepository.findByUserId(1)).thenReturn(Optional.of(annualLeave()));
        when(annualLeaveRepository.save(Mockito.any(AnnualLeave.class))).thenReturn(annualLeave());
        annualLeaveService.updateAnnualLeave(updateAnnualLeaveDto());
    }
}