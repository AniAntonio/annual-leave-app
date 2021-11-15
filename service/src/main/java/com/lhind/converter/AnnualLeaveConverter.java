package com.lhind.converter;

import com.lhind.dto.annualLeave.AnnualLeaveResponseDto;
import com.lhind.entities.AnnualLeave;

public class AnnualLeaveConverter {
    public static AnnualLeaveResponseDto toDto(AnnualLeave annualLeave) {
        AnnualLeaveResponseDto dto = new AnnualLeaveResponseDto();
        dto.setId(annualLeave.getId());
        dto.setUser(annualLeave.getUser());
        dto.setSupervisor(annualLeave.getSupervisor());
        dto.setRemainingDays(annualLeave.getRemainingDays());
        dto.setSpentDays(annualLeave.getSpentDays());
        dto.setEmploymentDate(annualLeave.getEmploymentDate());
        return dto;
    }
}
