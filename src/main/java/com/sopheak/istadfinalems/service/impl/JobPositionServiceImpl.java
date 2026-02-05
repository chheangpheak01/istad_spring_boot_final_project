package com.sopheak.istadfinalems.service.impl;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.JobPosition;
import com.sopheak.istadfinalems.entities.emun.AuditAction;
import com.sopheak.istadfinalems.exception.EmployeeNotFoundException;
import com.sopheak.istadfinalems.exception.JobPositionNotFoundException;
import com.sopheak.istadfinalems.mapper.JobPositionMapStruct;
import com.sopheak.istadfinalems.model.dto.job_positon.JobPositionCreateDto;
import com.sopheak.istadfinalems.model.dto.job_positon.JobPositionResponseDto;
import com.sopheak.istadfinalems.model.dto.job_positon.JobPositionUpdateDto;
import com.sopheak.istadfinalems.repository.EmployeeRepository;
import com.sopheak.istadfinalems.repository.JobPositionRepository;
import com.sopheak.istadfinalems.service.AuditService;
import com.sopheak.istadfinalems.service.JobPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobPositionServiceImpl implements JobPositionService {

    private final JobPositionRepository jobPositionRepository;
    private final JobPositionMapStruct jobPositionMapStruct;
    private final EmployeeRepository employeeRepository;
    private final AuditService auditService;

    @Override
    public JobPositionResponseDto getJobPositionByUuid(String uuid) {
        Optional<JobPosition> jobPosition = jobPositionRepository.findByUuidAndIsDeletedFalse(uuid);
        if (jobPosition.isEmpty()) {
            throw new JobPositionNotFoundException("Job Position not found with this uuid: " + uuid);
        }
        return jobPositionMapStruct.mapFromJobPositionToJobPositionResponseDto(jobPosition.get());
    }

    @Override
    public JobPositionResponseDto getJobPositionByTitle(String title) {
        Optional<JobPosition> jobPosition = jobPositionRepository.findByTitleAndIsDeletedFalse(title);
        if (jobPosition.isEmpty()) {
            throw new JobPositionNotFoundException("Job Position not found with this name: " + title);
        }
        return jobPositionMapStruct.mapFromJobPositionToJobPositionResponseDto(jobPosition.get());
    }

    @Override
    public Page<JobPositionResponseDto> getAllJobPositionByPagination(Pageable pageable) {
        Page<JobPosition> jobPositionPage = jobPositionRepository.findAllByIsDeletedFalse(pageable);
        if (jobPositionPage.isEmpty()) {
            throw new JobPositionNotFoundException("No Job Positions found for this page");
        }
        return jobPositionPage.map(jobPositionMapStruct::mapFromJobPositionToJobPositionResponseDto);
    }

    @Override
    public JobPositionResponseDto createJobPosition(JobPositionCreateDto createDto) {
        if (jobPositionRepository.existsByTitleAndIsDeletedFalse(createDto.title())) {
            throw new IllegalArgumentException("Title already exists: " +createDto.title());
        }

        JobPosition jobPosition = new JobPosition();

        jobPosition.setUuid(UUID.randomUUID().toString());
        jobPosition.setTitle(createDto.title());
        jobPosition.setMinSalary(createDto.minSalary());
        jobPosition.setMaxSalary(createDto.maxSalary());
        jobPosition.setIsDeleted(false);

        JobPosition savedJobPosition = jobPositionRepository.save(jobPosition);

        if (createDto.employeeUuids() != null) {
            if (jobPosition.getEmployees() == null) {
                jobPosition.setEmployees(new ArrayList<>());
            }

            for (String empUuid : createDto.employeeUuids()) {
                Employee employee = employeeRepository.findEmployeeByUuidAndIsDeletedIsFalse(empUuid)
                        .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with UUID: " + empUuid));
                employee.setJobPosition(jobPosition);
                savedJobPosition.getEmployees().add(employee);
                employeeRepository.save(employee);
            }
        }
        auditService.log(
                "JobPosition",
                jobPosition.getId().toString(),
                AuditAction.CREATE,
                "Created JobPositon: " + jobPosition.getTitle()
        );
        return jobPositionMapStruct.mapFromJobPositionToJobPositionResponseDto(savedJobPosition);
    }

    @Override
    public JobPositionResponseDto updateJobPositonByUuid(String uuid, JobPositionUpdateDto updateDto) {
        JobPosition jobPosition = jobPositionRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new JobPositionNotFoundException("Job Position not found with UUID: " + uuid));

        jobPosition.setTitle(updateDto.title());
        jobPosition.setMinSalary(updateDto.minSalary());
        jobPosition.setMaxSalary(updateDto.maxSalary());
        jobPosition.setIsDeleted(updateDto.isDeleted());

        if (updateDto.employeeUuids() != null) {
            List<Employee> currentEmployees = employeeRepository.findAllByJobPosition(jobPosition);
            for (Employee emp : currentEmployees) {
                emp.setJobPosition(null);
                employeeRepository.save(emp);
            }
            jobPosition.getEmployees().clear();

            for (String empUuid : updateDto.employeeUuids()) {
                Employee employee = employeeRepository.findEmployeeByUuidAndIsDeletedIsFalse(empUuid)
                        .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with UUID: " + empUuid));
                employee.setJobPosition(jobPosition);
                jobPosition.getEmployees().add(employee);
                employeeRepository.save(employee);
            }
        }
        auditService.log(
                "JobPosition",
                jobPosition.getId().toString(),
                AuditAction.UPDATE,
                "Updated job position details for: " + jobPosition.getTitle()
        );
        return jobPositionMapStruct.mapFromJobPositionToJobPositionResponseDto(jobPositionRepository.save(jobPosition));
    }

    @Override
    public String deleteJobPositionByUuid(String uuid) {
        JobPosition jobPosition = jobPositionRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new JobPositionNotFoundException("Job Position not found with UUID: " + uuid));
        jobPosition.setIsDeleted(true);
        jobPositionRepository.save(jobPosition);
        auditService.log(
                "JobPosition",
                jobPosition.getId().toString(),
                AuditAction.DELETE,
                "Soft deleted Job positon: " + jobPosition.getTitle()
        );
        return "Job positon with UUID " + uuid + " has been deleted successfully";
    }
}
