package com.sopheak.istadfinalems.service;
import com.sopheak.istadfinalems.model.dto.leave_request.LeaveRequestCreateDto;
import com.sopheak.istadfinalems.model.dto.leave_request.LeaveRequestResponseDto;
import com.sopheak.istadfinalems.model.dto.leave_request.LeaveRequestUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface LeaveRequestService {
    LeaveRequestResponseDto getLeaveRequestByUuid(String uuid);
    Page<LeaveRequestResponseDto> getAllLeaveRequestByPagination(Pageable pageable);
    LeaveRequestResponseDto createLeaveRequest(LeaveRequestCreateDto createDto);
    LeaveRequestResponseDto updateLeaveRequestByUuid(String uuid, LeaveRequestUpdateDto updateDto);
    String deleteLeaveRequestByUuid(String uuid);

}
