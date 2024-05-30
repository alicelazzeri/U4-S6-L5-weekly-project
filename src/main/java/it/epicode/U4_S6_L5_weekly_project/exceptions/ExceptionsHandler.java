package it.epicode.U4_S6_L5_weekly_project.exceptions;

import it.epicode.U4_S6_L5_weekly_project.payloads.ExceptionResponsePayload;
import it.epicode.U4_S6_L5_weekly_project.payloads.ExceptionsListResponsePayload;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice

public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ExceptionResponsePayload> handleException(Exception e) {
        ExceptionResponsePayload payload = new ExceptionResponsePayload(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
        ResponseEntity<ExceptionResponsePayload> responseEntity = new ResponseEntity<>(payload, HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ExceptionsListResponsePayload> handleBadRequestException(BadRequestException e) {
        ExceptionsListResponsePayload payload = new ExceptionsListResponsePayload(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),e.getExceptionsList());
        ResponseEntity<ExceptionsListResponsePayload> responseEntity = new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ExceptionResponsePayload> handleNotFoundException(NotFoundException e) {
        ExceptionResponsePayload payload = new ExceptionResponsePayload(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        ResponseEntity<ExceptionResponsePayload> responseEntity = new ResponseEntity<>(payload, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected ResponseEntity<ExceptionResponsePayload> handleNoContentException(NoContentException e) {
        ExceptionResponsePayload payload = new ExceptionResponsePayload(e.getMessage(), HttpStatus.NO_CONTENT, LocalDateTime.now());
        ResponseEntity<ExceptionResponsePayload> responseEntity = new ResponseEntity<>(payload, HttpStatus.NO_CONTENT);
        return responseEntity;
    }
}
