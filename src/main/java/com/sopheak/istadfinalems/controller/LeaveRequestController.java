package com.sopheak.istadfinalems.controller;
import com.sopheak.istadfinalems.model.dto.leave_request.LeaveRequestCreateDto;
import com.sopheak.istadfinalems.model.dto.leave_request.LeaveRequestUpdateDto;
import com.sopheak.istadfinalems.service.LeaveRequestService;
import com.sopheak.istadfinalems.utils.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@EnableMethodSecurity
@RequestMapping("/api/v1/leave-requests")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('user')")
    public ResponseTemplate<Object> getLeaveRequestByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Leave Request data retrieved successfully")
                .data(leaveRequestService.getLeaveRequestByUuid(uuid))
                .build();
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasRole('user')")
    public ResponseTemplate<Object> getAllLeaveRequestByPagination(Pageable pageable){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Leave Request data retrieved by pagination successfully")
                .data(leaveRequestService.getAllLeaveRequestByPagination(pageable))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('admin','super_admin')")
    public ResponseTemplate<Object> createLeaveRequest(@RequestBody @Validated LeaveRequestCreateDto createDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.CREATED.toString())
                .message("Leave Request data has been created successfully")
                .data(leaveRequestService.createLeaveRequest(createDto))
                .build();
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('admin','super_admin')")
    public ResponseTemplate<Object> updateLeaveRequestByUuid(@PathVariable String uuid, @RequestBody @Validated LeaveRequestUpdateDto updateDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Leave Request data has been updated successfully")
                .data(leaveRequestService.updateLeaveRequestByUuid(uuid, updateDto))
                .build();
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('admin','super_admin')")
    public ResponseTemplate<Object> deleteLeaveRequestByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message(leaveRequestService.deleteLeaveRequestByUuid(uuid))
                .data(null)
                .build();
    }
}
