package com.lhind.controller;


import com.lhind.security.AuthenticationService;
import com.lhind.security.LoginRequest;
import com.lhind.security.jwt.JwtProvider;
import com.lhind.util.Paths;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final JwtProvider jwtProvider;

    public AuthController(AuthenticationManager authenticationManager, AuthenticationService authenticationService, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping(value = Paths.LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        return ResponseEntity.ok(authenticationService.createResponse(authentication));
    }
}