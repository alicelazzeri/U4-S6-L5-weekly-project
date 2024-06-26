package it.epicode.U4_S6_L5_weekly_project.payloads;


import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponsePayload (
        String message,
        HttpStatus httpStatus,
        LocalDateTime createdAt
) {
    public ExceptionResponsePayload(String message, HttpStatus httpStatus, LocalDateTime createdAt) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.createdAt = createdAt;
    }
}
