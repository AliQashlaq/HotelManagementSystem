package com.example.hotelmanagementsystem.common.exception;

import com.example.hotelmanagementsystem.common.dto.ErrorResponseDTO;
import com.example.hotelmanagementsystem.room.RoomNotAvailableException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Code 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    // Code 403
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> authorizationDeniedException(AuthorizationDeniedException ex){
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
    // Code 401
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException ex){
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // Code 400 - handles @Valid on request DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        // get the list of failed field objects
        String validationErrors = ex.getBindingResult().getFieldErrors().stream()
                // format each error into "fieldName: error message"
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                // glue them together with commas into one readable sentence
                .collect(java.util.stream.Collectors.joining(", "));
        // wrap that unified string into our ErrorResponseDTO
        ErrorResponseDTO error = new ErrorResponseDTO(validationErrors, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(error);
    }
    // Code 400
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex){
        ErrorResponseDTO error = new ErrorResponseDTO("Database constraint violation: duplicate record or invalid reference"
                  , HttpStatus.BAD_REQUEST.value()); // hardcode the database error message to prevent severe security leaks
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException argumentEx){
        ErrorResponseDTO error = new ErrorResponseDTO(argumentEx.getMessage()
                , HttpStatus.BAD_REQUEST.value()); //
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalState(IllegalStateException stateEx){
        ErrorResponseDTO error = new ErrorResponseDTO(stateEx.getMessage()
                , HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(error);
    }
    // Code 404
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO> noResourceFoundException(NoResourceFoundException ex){
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    // Code 409
    @ExceptionHandler(RoomNotAvailableException.class)
    public ResponseEntity<ErrorResponseDTO> roomNotAvailableException(RoomNotAvailableException ex){
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    // Code 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO("An unexpected internal error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.internalServerError().body(error);
    }
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateResourceException(DuplicateResourceException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
