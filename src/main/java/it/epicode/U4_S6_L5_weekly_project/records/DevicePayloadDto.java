package it.epicode.U4_S6_L5_weekly_project.records;

import it.epicode.U4_S6_L5_weekly_project.entities.enums.DeviceStatus;
import it.epicode.U4_S6_L5_weekly_project.entities.enums.DeviceType;
import jakarta.validation.constraints.NotNull;

public record DevicePayloadDto(
        @NotNull(message = "Device type is mandatory.")
        DeviceType deviceType,
        @NotNull(message = "Device status is mandatory.")
        DeviceStatus deviceStatus,
        @NotNull(message = "The ID of the employee is mandatory.")
        long employeeId

) {
}
