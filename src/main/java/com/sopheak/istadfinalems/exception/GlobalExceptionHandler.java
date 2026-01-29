package com.sopheak.istadfinalems.exception;
import com.sopheak.istadfinalems.utils.AIPErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AIPErrorResponse> handleGeneralException(Exception e) {
        AIPErrorResponse aipErrorResponse = AIPErrorResponse
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .timeStamp(LocalDateTime.now())
                .errorMessage("Internal Server Error: " + e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(aipErrorResponse);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<AIPErrorResponse> EmployeeNotFoundHandling(EmployeeNotFoundException e){
        AIPErrorResponse aipErrorResponse = AIPErrorResponse
                .builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .timeStamp(LocalDateTime.now())
                .errorMessage(e.getMessage())
                .build();
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(aipErrorResponse);
    }

}
