package it.epicode.U4_S6_L5_weekly_project.payloads;

import it.epicode.U4_S6_L5_weekly_project.entities.enums.DeviceStatus;
import it.epicode.U4_S6_L5_weekly_project.entities.enums.DeviceType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AssignedDeviceToEmployeePayload(
        @NotNull(message = "Device ID is mandatory.")
        long deviceId,
        @NotNull(message = "Device type is mandatory.")
        DeviceType deviceType,
        @NotNull(message = "Device status is mandatory.")
        DeviceStatus deviceStatus,
        @NotNull(message = "Employee ID is mandatory.")
        long employeeId,
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
) {
}
