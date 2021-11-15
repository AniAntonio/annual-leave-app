package com.lhind.dto.annualLeave;

import com.lhind.entities.User;
import lombok.Data;

import java.util.Date;

@Data
public class AnnualLeaveResponseDto {
    private Integer id;

    private User user;

    private User supervisor;

    private Integer remainingDays;

    private Integer spentDays;

    private Date employmentDate;
}
