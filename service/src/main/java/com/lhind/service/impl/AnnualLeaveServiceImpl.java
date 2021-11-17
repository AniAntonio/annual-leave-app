package com.lhind.service.impl;

import com.lhind.converter.AnnualLeaveConverter;
import com.lhind.dto.annualLeave.AnnualLeaveResponseDto;
import com.lhind.dto.annualLeave.UpdateAnnualLeaveDto;
import com.lhind.dto.application.ApplicationFilterDto;
import com.lhind.dto.application.ApplicationResponseDto;
import com.lhind.dto.user.UserDto;
import com.lhind.entities.AnnualLeave;
import com.lhind.entities.User;
import com.lhind.enums.ApplicationStatus;
import com.lhind.enums.Role;
import com.lhind.exception.LhindNotFoundException;
import com.lhind.repository.AnnualLeaveRepository;
import com.lhind.repository.UserRepository;
import com.lhind.service.IAnnualLeaveService;
import com.lhind.service.IApplicationService;
import com.lhind.util.NoData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.lhind.util.DateUtil.today;
import static com.lhind.util.LhindUtil.TOTAL_LEAVE_DAYS;

@Slf4j
@Transactional
@Service
public class AnnualLeaveServiceImpl implements IAnnualLeaveService {

    @Autowired
    private AnnualLeaveRepository annualLeaveRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IApplicationService applicationService;

    @Override
    public AnnualLeaveResponseDto getAnnualLeaveByUserId(Integer idUser) {
        AnnualLeave annualLeave = annualLeaveRepository.findByUserId(idUser).orElseThrow(() -> new LhindNotFoundException(NoData.ANNUAL_LEAVE_NOT_FOUND));
        return AnnualLeaveConverter.toDto(annualLeave);
    }

    @Override
    public void insertAnnualLeave(UserDto userDto, User user) {
        log.info("Insert a row on annual leave for role USER");
        AnnualLeave annualLeave = new AnnualLeave();
        annualLeave.setUser(user);
        User supervisor = userRepository.findById(userDto.getSupervisorId()).orElseThrow(() -> new LhindNotFoundException(NoData.USER_NOT_FOUND));
        if (!supervisor.getRole().name().equals(Role.SUPERVISOR.name())) {
            throw new LhindNotFoundException(NoData.SUPERVISOR_NOT_FOUND);
        }
        annualLeave.setSupervisor(supervisor);
        annualLeave.setEmploymentDate(today());
        annualLeave.setRemainingDays(TOTAL_LEAVE_DAYS);
        annualLeave.setSpentDays(0);
        annualLeaveRepository.save(annualLeave);
        log.info("Annual leave inerted for user : {}", user.getUsername());
    }

    @Override
    public void updateAnnualLeave(UpdateAnnualLeaveDto updateAnnualLeaveDto) {
        log.info("Find annual leave for the user");
        AnnualLeave annualLeave = annualLeaveRepository.findByUserId(updateAnnualLeaveDto.getUserId()).orElseThrow(() -> new LhindNotFoundException(NoData.ANNUAL_LEAVE_NOT_FOUND));
        log.info("updating spentDays and remainingDays");
        annualLeave.setSpentDays(annualLeave.getSpentDays() + updateAnnualLeaveDto.getDaysOff());
        annualLeave.setRemainingDays(annualLeave.getRemainingDays() - updateAnnualLeaveDto.getDaysOff());
        annualLeaveRepository.save(annualLeave);
    }

    @Override
    public Integer getUserRemainingDays(Integer userId) {
        ApplicationFilterDto applicationFilterDto = new ApplicationFilterDto();
        applicationFilterDto.setUserId(userId);
        Integer daysOnOtherApplications = 0;
        for (ApplicationResponseDto app : applicationService.getApplications(applicationFilterDto)) {
            if (app.getStatus().name() != ApplicationStatus.REJECTED.name()) {
                daysOnOtherApplications = daysOnOtherApplications + app.getDaysOff();
            }
        }
        AnnualLeaveResponseDto annualLeaveResponseDto = this.getAnnualLeaveByUserId(userId);
        return annualLeaveResponseDto.getRemainingDays() - daysOnOtherApplications;
    }


}
