package com.sopheak.istadfinalems.controller;
import com.sopheak.istadfinalems.service.impl.AuthServiceImpl;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeCreateDto;
import com.sopheak.istadfinalems.model.dto.keycloak.ResetPasswordDto;
import com.sopheak.istadfinalems.utils.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;

@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@RestController
@EnableMethodSecurity
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('admin','super_admin')")
    public ResponseTemplate<Object> registerUser(@RequestBody EmployeeCreateDto employeeCreateDto){
        return ResponseTemplate
                .builder()
                .staus(HttpStatus.CREATED.toString())
                .date(Date.from(Instant.now()))
                .message("Create new user successfully")
                .data(authService.registerEmployee(employeeCreateDto))
                .build();
    }

    @PutMapping("/reset-password")
    @PreAuthorize("hasRole('user') or hasAnyRole('admin', 'super_admin')")
    public ResponseTemplate<Object> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto){
        return ResponseTemplate
                .builder()
                .staus(HttpStatus.CREATED.toString())
                .date(Date.from(Instant.now()))
                .message("Reset user password successfully")
                .data(authService.resetPassword(resetPasswordDto))
                .build();
    }

    @PostMapping("/forget-password")
    @PreAuthorize("hasRole('user') or hasAnyRole('admin', 'super_admin')")
    public ResponseTemplate<Object> forgetPassword(@RequestParam String email){
        return ResponseTemplate
                .builder()
                .staus(HttpStatus.CREATED.toString())
                .date(Date.from(Instant.now()))
                .message("Check your email to forget your password")
                .data(authService.forgotPassword(email))
                .build();
    }
}