package br.com.alura.AluraFake.config.exception;

import br.com.alura.AluraFake.config.exception.dto.ErrorDTO;
import br.com.alura.AluraFake.config.exception.exceptions.ServiceException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO.Response.Error> handlerMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> map = new HashMap<>();
        var fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(
                new ErrorDTO.Response.Error(BAD_REQUEST.value(), ex.getClass().getSimpleName(), map));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO.Response.Error> handlerEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(
                new ErrorDTO.Response.Error(NOT_FOUND.value(), ex.getClass().getSimpleName(), ex.getMessage()));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorDTO.Response.Error> handlerService(ServiceException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(
                new ErrorDTO.Response.Error(BAD_REQUEST.value(), ex.getClass().getSimpleName(), ex.getMessage()));
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ErrorDTO.Response.Error> handlerBadCredentials(BadCredentialsException ex) {
//        return ResponseEntity.status(UNAUTHORIZED).body(
//                new ErrorDTO.Response.Error(UNAUTHORIZED.value(), ex.getClass().getSimpleName(), ex.getMessage()));
//    }
}
