package com.fageniuscode.epargneplus.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    // Gestion des exceptions spécifiques à l'inscription
    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<Object> handleRegistrationException(RegistrationException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    // Gestion des exceptions spécifiques à la connexion
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Object> handleLoginException(LoginException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    // Gestion des exceptions spécifiques à la gestion des comptes
    @ExceptionHandler(AccountManagementException.class)
    public ResponseEntity<Object> handleAccountManagementException(AccountManagementException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // Gestion des exceptions générales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Une erreur s'est produite lors du traitement de la demande.", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // Gestion des exceptions de requête
    @ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<APIException> handleRequestException(RequestException e) {
        APIException exception = new APIException(e.getMessage(), e.getStatus(), LocalDateTime.now());
        return new ResponseEntity<>(exception, e.getStatus());
    }

    // Gestion des exceptions liées à l'absence de ressource (404)
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<APIException> handleEntityNotFoundException(EntityNotFoundException e) {
        APIException exception = new APIException(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    // Gestion des exceptions liées à des erreurs de conversion de nombre (400)
    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<APIException> handleNumberFormatException(NumberFormatException e) {
        APIException exception = new APIException(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    // Gestion des erreurs de validation des données (400)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessages = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessages.add(error.getDefaultMessage());
        }
        return handleExceptionInternal(ex, errorMessages, headers, HttpStatus.BAD_REQUEST, request);
    }


    // Gestion des erreurs d'intégrité des données (400)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String errorMessage = "Erreur d'intégrité des données : " + ex.getRootCause().getMessage();
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}

