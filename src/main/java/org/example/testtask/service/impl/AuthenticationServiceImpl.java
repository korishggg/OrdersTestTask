package org.example.testtask.service.impl;

import org.example.testtask.entity.Role;
import org.example.testtask.entity.User;
import org.example.testtask.exception.UserAlreadyExistsException;
import org.example.testtask.model.CreateUserRequest;
import org.example.testtask.model.LoginRequest;
import org.example.testtask.model.LoginResponse;
import org.example.testtask.repositories.UserRepository;
import org.example.testtask.security.CustomUserDetails;
import org.example.testtask.security.JwtService;
import org.example.testtask.service.AuthenticationService;
import org.example.testtask.utils.Mappers;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        var principal = (CustomUserDetails) authentication.getPrincipal();
        var accessToken = jwtService.generateToken(principal.getUsername(), false);
        var refreshToken = jwtService.generateToken(principal.getUsername(), true);
        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    public void registerUser(CreateUserRequest request) {
        validateUserRegistration(request);
        User user = Mappers.createUserRequesToUser(request, passwordEncoder.encode(request.password()), Role.USER);
        userRepository.save(user);
    }

    private void validateUserRegistration(CreateUserRequest request) {
        if(userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email =" + request.email() + " already exists");
        }
    }
}
