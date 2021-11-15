package com.lhind.dto.application;

import lombok.Data;

import java.util.Date;

import static com.lhind.util.DateUtil.convertStringDateToLocalDate;

@Data
public class ApplicationDto {
    private Integer daysOff;
    private Date startDate;

    public ApplicationDto(String startDate, Integer daysOff) {
        this.startDate = startDate != null ? convertStringDateToLocalDate(startDate) : null;
        this.daysOff = daysOff;
    }
}
