package com.lhind.converter;

import com.lhind.dto.application.ApplicationDto;
import com.lhind.dto.application.ApplicationResponseDto;
import com.lhind.entities.Application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationConverter {
    public static Application toEntity(ApplicationDto applicationDto) {
        Application application = new Application();
        application.setDaysOff(applicationDto.getDaysOff());
        return application;
    }

    public static ApplicationResponseDto toApplicationResponse(Application application) {
        ApplicationResponseDto applicationResponseDto = new ApplicationResponseDto();
        applicationResponseDto.setId(application.getId());
        applicationResponseDto.setUser(UserConverter.toDto(application.getUser()));
        applicationResponseDto.setSupervisor(UserConverter.toDto(application.getSupervisor()));
        applicationResponseDto.setDaysOff(application.getDaysOff());
        applicationResponseDto.setStatus(application.getStatus());
        applicationResponseDto.setComment(application.getComment());
        applicationResponseDto.setStartDate(application.getStartDate());
        applicationResponseDto.setEndDate(application.getEndDate());
        return applicationResponseDto;
    }

    public static List<ApplicationResponseDto> toApplicationResponseDtoList(List<Application> applications) {
        List<ApplicationResponseDto> applicationResponseDtos = new ArrayList<>();
        applications.forEach(app -> {
            applicationResponseDtos.add(toApplicationResponse(app));
        });
        return applicationResponseDtos;
    }
}
