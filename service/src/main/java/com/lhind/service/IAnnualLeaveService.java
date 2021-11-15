package com.lhind.service;

import com.lhind.dto.annualLeave.AnnualLeaveResponseDto;
import com.lhind.dto.annualLeave.UpdateAnnualLeaveDto;
import com.lhind.dto.user.UserDto;
import com.lhind.entities.User;

public interface IAnnualLeaveService {

    AnnualLeaveResponseDto getAnnualLeaveByUserId(Integer idUser);

    void insertAnnualLeave(UserDto userDto, User user);

    void updateAnnualLeave(UpdateAnnualLeaveDto updateAnnualLeaveDto);
}
