package it.epicode.U4_S6_L5_weekly_project.records;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EmployeePayloadDto(
        @NotNull(message = "Employee username is mandatory.")
        @NotEmpty(message = "Employee username cannot be empty.")
        String username,
        @NotNull(message = "Employee name is mandatory.")
        @NotEmpty(message = "Employee name cannot be empty.")
        String firstName,
        @NotNull(message = "Employee surname is mandatory.")
        @NotEmpty(message = "Employee surname cannot be empty.")
        String lastName,
        @NotNull(message = "Employee email is mandatory.")
        @Email(message = "Employee email must be a valid email address.")
        String email,
        String avatar
) {}
