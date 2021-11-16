package com.lhind.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhind.dto.user.UserResponseDto;
import com.lhind.enums.ApplicationStatus;
import lombok.Data;

import java.util.Date;

import static com.lhind.util.RegexPatterns.DATE_PATTERN;

@Data
public class ApplicationResponseDto {
    private Integer id;

    private UserResponseDto user;

    private UserResponseDto supervisor;

    private Integer daysOff;

    private ApplicationStatus status;

    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "CET")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "CET")
    private Date endDate;
}
