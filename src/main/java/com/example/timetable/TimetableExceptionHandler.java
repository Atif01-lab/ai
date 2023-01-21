package com.example.timetable;

import com.example.timetable.exception.ClassroomNotAvailableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class TimetableExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler(ClassroomNotAvailableException.class)
    public ResponseEntity<Object> handleClassroomNotAvailable(ClassroomNotAvailableException ex, WebRequest request) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}