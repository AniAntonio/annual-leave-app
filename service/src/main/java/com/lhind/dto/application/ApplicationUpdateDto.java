package com.lhind.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

import static com.lhind.util.RegexPatterns.DATE_PATTERN;

@Data
public class ApplicationUpdateDto {

    private Integer daysOff;

    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private Date startDate;
}
