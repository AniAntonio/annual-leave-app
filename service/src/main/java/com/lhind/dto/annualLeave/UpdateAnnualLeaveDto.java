package com.lhind.dto.annualLeave;

import lombok.Data;

@Data
public class UpdateAnnualLeaveDto {
    private Integer userId;

    private Integer daysOff;
}
