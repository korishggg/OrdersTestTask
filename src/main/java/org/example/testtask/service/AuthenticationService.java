package org.example.testtask.service;

import org.example.testtask.model.CreateUserRequest;
import org.example.testtask.model.LoginRequest;
import org.example.testtask.model.LoginResponse;

public interface AuthenticationService {

    LoginResponse authenticate(LoginRequest request);
    void registerUser(CreateUserRequest request);
}
