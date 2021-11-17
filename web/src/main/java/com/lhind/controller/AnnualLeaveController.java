package com.lhind.controller;

import com.lhind.service.IAnnualLeaveService;
import com.lhind.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Paths.ANNUAL_LEAVE)
public class AnnualLeaveController {

    @Autowired
    private IAnnualLeaveService annualLeaveService;

    @GetMapping("/remainingDays/{userId}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<Integer> getMyRemainingDays(@PathVariable Integer userId) {
        return ResponseEntity.ok(annualLeaveService.getUserRemainingDays(userId));
    }
}
