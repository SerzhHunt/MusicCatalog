package com.epam.musiccatalog.exception.handler;

import com.epam.musiccatalog.exception.AlbumNotFoundException;
import com.epam.musiccatalog.exception.AuthorNotFoundException;
import com.epam.musiccatalog.exception.SongNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({AuthorNotFoundException.class, AlbumNotFoundException.class, SongNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> onMethodArgumentNotValidException(BindException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " - " + error.getDefaultMessage())
                .collect(Collectors.joining("\n"));

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error("Invalid input parameters")
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
