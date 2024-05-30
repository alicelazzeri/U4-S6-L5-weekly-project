package it.epicode.U4_S6_L5_weekly_project.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EmployeePayload(
        @NotNull(message = "Employee username is mandatory.")
        @NotEmpty(message = "Employee username cannot be empty.")
        String username,
        @NotNull(message = "Employee firstName is mandatory.")
        @NotEmpty(message = "Employee firstName cannot be empty.")
        String firstName,
        @NotNull(message = "Employee lastName is mandatory.")
        @NotEmpty(message = "Employee lastName cannot be empty.")
        String lastName,
        @NotNull(message = "Employee email is mandatory.")
        @Email(message = "Employee email must be a valid email address.")
        String email,
        String avatar
) {}
