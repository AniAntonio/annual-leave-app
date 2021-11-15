package com.lhind.dto.application;

import com.lhind.enums.ApplicationStatus;
import lombok.Data;

@Data
public class RejectApplicationDto {
    private Integer id;

    private ApplicationStatus status;

    private String comment;
}
