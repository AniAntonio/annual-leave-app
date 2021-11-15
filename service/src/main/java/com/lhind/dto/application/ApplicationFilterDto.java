package com.lhind.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhind.enums.ApplicationStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ApplicationFilterDto {
    private ApplicationStatus status;

    private Integer daysOff;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date applicationDateFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date applicationDateTo;

    private Integer userId;

    private Integer supervisorId;
}
