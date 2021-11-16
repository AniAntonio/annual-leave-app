package com.lhind.controller;

import com.lhind.dto.application.*;
import com.lhind.security.UserPrincipal;
import com.lhind.service.IApplicationService;
import com.lhind.util.AuthenticationFacade;
import com.lhind.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lhind.util.ExcelDownloadableResponse.excel;

@RestController
@RequestMapping(value = Paths.APPLICATION)
public class ApplicationController {

    @Autowired
    private IApplicationService applicationService;

    private final AuthenticationFacade authenticationFacade;

    public ApplicationController(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping("/user/createApplication")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ApplicationResponseDto> createApplication(@RequestParam(value = "startDate") String startDate, @RequestParam(value = "daysOff") Integer daysOff) {
        UserPrincipal userPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
        ApplicationDto applicationDto = new ApplicationDto(startDate, daysOff);
        return ResponseEntity.ok(applicationService.createApplication(applicationDto, userPrincipal.getId()));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ApplicationResponseDto> updateApplication(@RequestBody ApplicationUpdateDto applicationUpdateDto) {
        UserPrincipal userPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
        return ResponseEntity.ok(applicationService.updateApplication(applicationUpdateDto, userPrincipal.getId()));

    }

    @DeleteMapping(Paths.BY_ID)
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<Void> deleteApplication(@PathVariable Integer id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/approve/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public ResponseEntity<ApplicationResponseDto> approveApplication(@PathVariable Integer id) {
        return ResponseEntity.ok(applicationService.approveApplication(id));
    }

    @PostMapping("/reject")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public ResponseEntity<ApplicationResponseDto> rejectApplication(@RequestBody RejectApplicationDto rejectApplicationDto) {
        return ResponseEntity.ok(applicationService.rejectApplication(rejectApplicationDto));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SUPERVISOR','USER')")
    public ResponseEntity<List<ApplicationResponseDto>> getApplications(@RequestBody ApplicationFilterDto applicationFilterDto) {
        return ResponseEntity.ok(applicationService.getApplications(applicationFilterDto));
    }

    @PostMapping("/generateXlsx")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public ResponseEntity<Resource> generateXlsx(@RequestBody ApplicationFilterDto applicationFilterDto) {
        return excel("Application_Report", applicationService.generateXlsx(applicationFilterDto));
    }
}
