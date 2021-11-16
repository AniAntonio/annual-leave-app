package com.lhind.service;

import com.lhind.dto.application.*;
import org.springframework.core.io.Resource;

import java.util.List;

public interface IApplicationService {

    ApplicationResponseDto createApplication(ApplicationDto applicationDto, Integer userId);

    ApplicationResponseDto updateApplication(ApplicationUpdateDto applicationUpdateDto, Integer userId);

    void deleteApplication(Integer id);

    ApplicationResponseDto approveApplication(Integer id);

    ApplicationResponseDto rejectApplication(RejectApplicationDto approveApplicationDto);

    List<ApplicationResponseDto> getApplications(ApplicationFilterDto applicationFilterDto);

    Resource generateXlsx(ApplicationFilterDto applicationFilterDto);

}
