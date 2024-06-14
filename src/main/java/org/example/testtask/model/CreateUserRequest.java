package org.example.testtask.model;

public record CreateUserRequest(String firstName,
                                String lastName,
                                String password,
                                String email) {
}
