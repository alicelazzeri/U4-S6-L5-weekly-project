package it.epicode.U4_S6_L5_weekly_project.payloads;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;

public record ExceptionsListResponsePayload(
        String message,
        HttpStatus httpStatus,
        LocalDateTime createdAt,
        List<ObjectError> exceptionsList
) {
}
