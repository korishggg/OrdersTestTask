package org.example.testtask.controller;

import org.example.testtask.model.CreateUserRequest;
import org.example.testtask.model.LoginRequest;
import org.example.testtask.model.LoginResponse;
import org.example.testtask.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserRequest request) {
        authenticationService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}