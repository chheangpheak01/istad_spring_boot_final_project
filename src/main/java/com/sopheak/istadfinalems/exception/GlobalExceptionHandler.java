package com.sopheak.istadfinalems.exception;
import com.sopheak.istadfinalems.utils.AIPErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleForDataNotSubmitting(HttpMessageNotReadableException exception){
        return "You missed the data submit";
    }

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

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<AIPErrorResponse> DepartmentNotFoundHandling(DepartmentNotFoundException e){
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

    @ExceptionHandler(JobPositionNotFoundException.class)
    public ResponseEntity<AIPErrorResponse> JobPositionNotFoundHandling(JobPositionNotFoundException e){
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

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<AIPErrorResponse> ProjectNotFoundHandling(ProjectNotFoundException e){
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

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<AIPErrorResponse> AddressNotFoundHandling(AddressNotFoundException e){
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

    @ExceptionHandler(EmployeeDocumentNotFoundException.class)
    public ResponseEntity<AIPErrorResponse> EmployeeDocumentNotFoundException(EmployeeDocumentNotFoundException e){
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

    @ExceptionHandler(LeaveRequestNotFoundException.class)
    public ResponseEntity<AIPErrorResponse> LeaveRequestNotFoundException(LeaveRequestNotFoundException e){
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AIPErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String detailedMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(java.util.stream.Collectors.joining(", "));
        AIPErrorResponse aipErrorResponse = AIPErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .timeStamp(LocalDateTime.now())
                .errorMessage("Validation Failed: " + detailedMessage)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(aipErrorResponse);
    }

}
