package com.lhind.service.impl.mock;

import com.lhind.dto.annualLeave.UpdateAnnualLeaveDto;
import com.lhind.dto.user.UpdatePasswordDto;
import com.lhind.dto.user.UserDto;
import com.lhind.dto.user.UserUpdateDto;
import com.lhind.entities.AnnualLeave;
import com.lhind.entities.User;
import com.lhind.enums.Role;

public class MockObject {
    public static UserDto userDto() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("Ani");
        userDto.setLastName("Ballabani");
        userDto.setUsername("ani95");
        userDto.setPassword("Ani95+");
        userDto.setSupervisorId(5);
        userDto.setRole(Role.USER);
        userDto.setEmail("ani@gmail.com");
        return userDto;
    }

    public static User user() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Ani");
        user.setLastName("Ballabani");
        user.setUsername("ani95");
        user.setPassword("Ani95+");
        user.setRole(Role.USER);
        user.setEmail("ani@gmail.com");
        return user;
    }

    public static UserUpdateDto userUpdateDto() {
        UserUpdateDto user = new UserUpdateDto();
        user.setFirstName("Ani");
        user.setLastName("Ballabani");
        user.setEmail("ani@gmail.com");
        return user;
    }

    public static UpdateAnnualLeaveDto updateAnnualLeaveDto(){
        UpdateAnnualLeaveDto updateAnnualLeaveDto = new UpdateAnnualLeaveDto();
        updateAnnualLeaveDto.setUserId(1);
        updateAnnualLeaveDto.setDaysOff(5);
        return updateAnnualLeaveDto;
    }

    public static AnnualLeave annualLeave(){
        AnnualLeave annualLeave = new AnnualLeave();
        annualLeave.setRemainingDays(20);
        annualLeave.setSpentDays(0);
        return annualLeave;
    }


}
