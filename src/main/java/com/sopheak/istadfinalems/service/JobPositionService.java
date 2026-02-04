package com.sopheak.istadfinalems.service;
import com.sopheak.istadfinalems.model.dto.job_positon.JobPositionCreateDto;
import com.sopheak.istadfinalems.model.dto.job_positon.JobPositionResponseDto;
import com.sopheak.istadfinalems.model.dto.job_positon.JobPositionUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface JobPositionService {
    JobPositionResponseDto getJobPositionByUuid(String uuid);
    JobPositionResponseDto getJobPositionByTitle(String title);
    Page<JobPositionResponseDto> getAllJobPositionByPagination(Pageable pageable);
    JobPositionResponseDto createJobPosition(JobPositionCreateDto createDto);
    JobPositionResponseDto updateJobPositonByUuid(String uuid, JobPositionUpdateDto updateDto);
    String deleteJobPositionByUuid(String uuid);
}