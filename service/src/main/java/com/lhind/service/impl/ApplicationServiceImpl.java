package com.lhind.service.impl;

import com.lhind.converter.ApplicationConverter;
import com.lhind.dto.annualLeave.AnnualLeaveResponseDto;
import com.lhind.dto.annualLeave.UpdateAnnualLeaveDto;
import com.lhind.dto.application.*;
import com.lhind.entities.Application;
import com.lhind.enums.ApplicationStatus;
import com.lhind.exception.InputException;
import com.lhind.exception.LhindNotFoundException;
import com.lhind.repository.ApplicationRepository;
import com.lhind.service.EmailService;
import com.lhind.service.IAnnualLeaveService;
import com.lhind.service.IApplicationService;
import com.lhind.util.BadRequest;
import com.lhind.util.NoData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.lhind.service.specification.ApplicationSpecification.getUserApplications;
import static com.lhind.util.DateUtil.*;
import static com.lhind.util.LhindUtil.PROBATION_PERIOD_DAYS;

@Slf4j
@Transactional
@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private IAnnualLeaveService annualLeaveService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ExcelServiceImpl excelService;


    @Override
    public ApplicationResponseDto createApplication(ApplicationDto applicationDto, Integer userId) {
        log.info("Getting annual leave data for the user!");
        AnnualLeaveResponseDto annualLeaveResponseDto = annualLeaveService.getAnnualLeaveByUserId(userId);
        log.info("Checking probation period!");
        if (beforeToday(PROBATION_PERIOD_DAYS).before(annualLeaveResponseDto.getEmploymentDate())) {
            throw new InputException(BadRequest.PROBATION_PERIOD_NOT_PASSED);
        }
        log.info("Probation period check passed");
        Date startDate = applicationDto.getStartDate();
        log.info("Getting previous applications if any!");
        Integer daysOnOtherApplications = getPreviousApplicationDaysOff(userId, startDate);
        log.info("Calculated previous application daysOff requested!");
        if (applicationDto.getDaysOff() + daysOnOtherApplications > annualLeaveResponseDto.getRemainingDays()) {
            throw new InputException(BadRequest.NOT_SUFFICIENT_DAYS);
        }
        Application application = createApplication(applicationDto, annualLeaveResponseDto);
        sendEmail(application);
        return ApplicationConverter.toApplicationResponse(applicationRepository.save(application));
    }

    private void sendEmail(Application application) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String emailText = "Hello " + application.getSupervisor().getFirstName() + " ,\n";
        emailText = emailText.concat("I would like to request for " + application.getDaysOff()).concat(" daysOff from date: ").concat(formatter.format(application.getStartDate())).concat(" until: ").concat(formatter.format(application.getEndDate())).concat(" [Included] !\n");
        emailText = emailText.concat("Thank you ,\n").concat(application.getUser().getFirstName() + " " + application.getUser().getLastName());
        emailService.sendSimpleMessage(application.getSupervisor().getEmail(), "New Leave Request", emailText);
    }


    @Override
    public ApplicationResponseDto updateApplication(ApplicationUpdateDto applicationUpdateDto, Integer userId) {
        log.info("Getting annual leave data for the user!");
        AnnualLeaveResponseDto annualLeaveResponseDto = annualLeaveService.getAnnualLeaveByUserId(userId);
        Date startDate = applicationUpdateDto.getStartDate();
        log.info("Getting previous applications if any!");
        Integer daysOnOtherApplications = getPreviousApplicationDaysOff(userId, startDate);
        log.info("Calculated previous application daysOff requested!");
        if (applicationUpdateDto.getDaysOff() + daysOnOtherApplications > annualLeaveResponseDto.getRemainingDays()) {
            throw new InputException(BadRequest.NOT_SUFFICIENT_DAYS);
        }
        log.info("Finding application by Id!!");
        Application application = applicationRepository.findById(applicationUpdateDto.getId()).orElseThrow(() -> new LhindNotFoundException(NoData.APPLICATION_NOT_FOUND));
        log.info("Checking if application is in REQUESTED status!");
        if (!application.getStatus().name().equals(ApplicationStatus.REQUESTED.name())) {
            throw new InputException(BadRequest.APPLICATION_REJECTED);
        }
        log.info("Check passed!");
        application.setDaysOff(applicationUpdateDto.getDaysOff());
        application.setStartDate(applicationUpdateDto.getStartDate());
        application.setEndDate(addDays(applicationUpdateDto.getStartDate(), applicationUpdateDto.getDaysOff()));
        return ApplicationConverter.toApplicationResponse(applicationRepository.save(application));
    }

    private Integer getPreviousApplicationDaysOff(Integer userId, Date startDate) {
        List<Application> previewsApplications = applicationRepository.getRequestedApplications(userId, ApplicationStatus.REQUESTED);
        Integer daysOnOtherApplications = 0;
        if (!previewsApplications.isEmpty()) {
            for (Application app : previewsApplications
            ) {
                log.info("Getting previous applications if any!");
                if ((startDate.after(app.getStartDate()) || startDate.equals(app.getStartDate()))
                        && (startDate.before(app.getEndDate()) || startDate.equals(app.getEndDate()))
                        || startDate.before(today())) {
                    throw new InputException(BadRequest.APPLICATION_ALREADY_REQUESTED);
                }
                daysOnOtherApplications = daysOnOtherApplications + app.getDaysOff();
            }
        }
        return daysOnOtherApplications;
    }

    @Override
    public void deleteApplication(Integer id) {
        if (applicationRepository.userExistsWithId(id)) {
            log.info("Setting flag deleted to true for the application {}!", id);
            applicationRepository.delete(id);
        } else {
            throw new LhindNotFoundException(NoData.USER_NOT_FOUND);
        }
    }

    @Override
    public ApplicationResponseDto approveApplication(Integer id) {
        log.info("Finding application");
        Application application = applicationRepository.findById(id).orElseThrow(() -> new LhindNotFoundException(NoData.APPLICATION_NOT_FOUND));
        application.setStatus(ApplicationStatus.APPROVED);
        updateAnnualLeave(application);
        this.sendEmailToUser(application);
        return ApplicationConverter.toApplicationResponse(applicationRepository.save(application));
    }

    private void updateAnnualLeave(Application application) {
        log.info("Update Annual leave when approving application");
        UpdateAnnualLeaveDto updateAnnualLeaveDto = new UpdateAnnualLeaveDto();
        updateAnnualLeaveDto.setDaysOff(application.getDaysOff());
        updateAnnualLeaveDto.setUserId(application.getUser().getId());
        annualLeaveService.updateAnnualLeave(updateAnnualLeaveDto);
    }

    @Override
    public ApplicationResponseDto rejectApplication(RejectApplicationDto rejectApplicationDto) {
        Application application = applicationRepository.findById(rejectApplicationDto.getId()).orElseThrow(() -> new LhindNotFoundException(NoData.APPLICATION_NOT_FOUND));
        application.setStatus(ApplicationStatus.REJECTED);
        application.setComment(rejectApplicationDto.getComment());
        this.sendEmailToUser(application);
        return ApplicationConverter.toApplicationResponse(applicationRepository.save(application));
    }


    private void sendEmailToUser(Application application) {
        String emailText = "Hello " + application.getUser().getFirstName() + " ,\n";
        emailText = emailText.concat("Your Application has been : " + application.getStatus().name() + "!\n");
        emailText = emailText.concat("Thank you ,\n").concat(application.getSupervisor().getFirstName() + " " + application.getSupervisor().getLastName());
        emailService.sendSimpleMessage(application.getUser().getEmail(), "Leave Request Update", emailText);
    }

    @Override
    public List<ApplicationResponseDto> getApplications(ApplicationFilterDto applicationFilterDto) {
        log.info("Getting my Applications!");
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        applicationRepository.findAll(getUserApplications(applicationFilterDto), pageable);
        return ApplicationConverter.toApplicationResponseDtoList(applicationRepository.findAll(getUserApplications(applicationFilterDto), pageable).getContent());
    }

    public Resource generateXlsx(ApplicationFilterDto applicationFilterDto) {
        log.info("Getting my Applications!");
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        List<ApplicationResponseDto> applicationResponseDtos = ApplicationConverter.toApplicationResponseDtoList(applicationRepository.findAll(getUserApplications(applicationFilterDto), pageable).getContent());
        return excelService.generateXlsx(applicationResponseDtos);
    }


    private Application createApplication(ApplicationDto applicationDto, AnnualLeaveResponseDto annualLeaveResponseDto) {
        Application application = new Application();
        application.setUser(annualLeaveResponseDto.getUser());
        application.setSupervisor(annualLeaveResponseDto.getSupervisor());
        application.setDaysOff(applicationDto.getDaysOff());
        application.setStatus(ApplicationStatus.REQUESTED);
        application.setFlagDeleted(Boolean.FALSE);
        application.setStartDate(applicationDto.getStartDate());
        application.setEndDate(addDays(applicationDto.getStartDate(), applicationDto.getDaysOff()));
        return application;
    }
}
