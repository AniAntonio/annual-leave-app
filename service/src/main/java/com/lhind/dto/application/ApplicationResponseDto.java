package com.lhind.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhind.dto.user.UserResponseDto;
import com.lhind.enums.ApplicationStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ApplicationResponseDto {
    private Integer id;

    private UserResponseDto user;

    private UserResponseDto supervisor;

    private Integer daysOff;

    private ApplicationStatus status;

    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
}
