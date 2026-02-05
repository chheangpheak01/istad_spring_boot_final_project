package com.sopheak.istadfinalems.service.impl;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.LeaveRequest;
import com.sopheak.istadfinalems.entities.emun.AuditAction;
import com.sopheak.istadfinalems.entities.emun.LeaveStatus;
import com.sopheak.istadfinalems.exception.EmployeeNotFoundException;
import com.sopheak.istadfinalems.exception.LeaveRequestNotFoundException;
import com.sopheak.istadfinalems.mapper.LeaveRequestMapStruct;
import com.sopheak.istadfinalems.model.dto.leave_request.LeaveRequestCreateDto;
import com.sopheak.istadfinalems.model.dto.leave_request.LeaveRequestResponseDto;
import com.sopheak.istadfinalems.model.dto.leave_request.LeaveRequestUpdateDto;
import com.sopheak.istadfinalems.repository.EmployeeRepository;
import com.sopheak.istadfinalems.repository.LeaveRequestRepository;
import com.sopheak.istadfinalems.service.AuditService;
import com.sopheak.istadfinalems.service.LeaveRequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveRequestMapStruct leaveRequestMapper;
    private final EmployeeRepository employeeRepository;
    private final AuditService auditService;

    @Override
    public LeaveRequestResponseDto getLeaveRequestByUuid(String uuid) {
        Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findByUuidAndIsDeletedFalse(uuid);
        if (leaveRequest.isEmpty()) {
            throw new LeaveRequestNotFoundException("Leave Request not found with this uuid: " + uuid);
        }
        return leaveRequestMapper.mapLeaveRequestToLeaveRequestResponseDto(leaveRequest.get());
    }

    @Override
    public Page<LeaveRequestResponseDto> getAllLeaveRequestByPagination(Pageable pageable) {
        Page<LeaveRequest> leaveRequestPage = leaveRequestRepository.findAllByIsDeletedFalse(pageable);
        if (leaveRequestPage.isEmpty()) {
            throw new LeaveRequestNotFoundException("No Leave Requests found for this page");
        }
        return leaveRequestPage.map(leaveRequestMapper::mapLeaveRequestToLeaveRequestResponseDto);
    }

    @Override
    @Transactional
    public LeaveRequestResponseDto createLeaveRequest(LeaveRequestCreateDto createDto) {
        Employee employee = employeeRepository.findEmployeeByUuidAndIsDeletedIsFalse(createDto.employeeUuid())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with UUID: " + createDto.employeeUuid()));

        LeaveRequest leaveRequest = new LeaveRequest();

        leaveRequest.setUuid(UUID.randomUUID().toString());
        leaveRequest.setStartDate(createDto.startDate());
        leaveRequest.setEndDate(createDto.endDate());
        leaveRequest.setReason(createDto.reason());
        leaveRequest.setStatus(LeaveStatus.PENDING);
        leaveRequest.setIsDeleted(false);
        leaveRequest.setEmployee(employee);

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);

        auditService.log(
                "LeaveRequest",
                savedLeaveRequest.getId().toString(),
                AuditAction.CREATE,
                "Created Leave Request for Employee: " + employee.getName() +
                        " [Reason: " + savedLeaveRequest.getReason() + "]"
        );
        return leaveRequestMapper.mapLeaveRequestToLeaveRequestResponseDto(savedLeaveRequest);
    }

    @Override
    @Transactional
    public LeaveRequestResponseDto updateLeaveRequestByUuid(String uuid, LeaveRequestUpdateDto updateDto) {
        LeaveRequest leaveRequest = leaveRequestRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new LeaveRequestNotFoundException("Leave Request not found with UUID: " + uuid));

        leaveRequest.setStartDate(updateDto.startDate());
        leaveRequest.setEndDate(updateDto.endDate());
        leaveRequest.setReason(updateDto.reason());
        leaveRequest.setIsDeleted(updateDto.isDeleted());

        if (updateDto.status() != null) {
            try {
                leaveRequest.setStatus(LeaveStatus.valueOf(updateDto.status().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status provided: " + updateDto.status());
            }
        }
        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        auditService.log(
                "LeaveRequest",
                savedLeaveRequest.getId().toString(),
                AuditAction.UPDATE,
                "Updated leave request details for UUID: " + uuid + " (New Status: " + savedLeaveRequest.getStatus() + ")"
        );
        return leaveRequestMapper.mapLeaveRequestToLeaveRequestResponseDto(savedLeaveRequest);
    }

    @Override
    public String deleteLeaveRequestByUuid(String uuid) {
        LeaveRequest leaveRequest = leaveRequestRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new LeaveRequestNotFoundException("Leave Request not found with UUID: " + uuid));
        leaveRequest.setIsDeleted(true);
        leaveRequestRepository.save(leaveRequest);
        auditService.log(
                "LeaveRequest",
                leaveRequest.getId().toString(),
                AuditAction.DELETE,
                "Soft deleted leave request with UUID: " + uuid);
        return "Leave request with UUID " + uuid + " has been deleted successfully";
    }
}
