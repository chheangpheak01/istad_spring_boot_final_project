package com.sopheak.istadfinalems.controller;
import com.sopheak.istadfinalems.model.dto.job_positon.JobPositionCreateDto;
import com.sopheak.istadfinalems.model.dto.job_positon.JobPositionUpdateDto;
import com.sopheak.istadfinalems.service.JobPositionService;
import com.sopheak.istadfinalems.utils.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobPositon")
public class JobPositionController {

    private final JobPositionService jobPositionService;

    @GetMapping("/get-jobPosition-by-uuid/{uuid}")
    public ResponseTemplate<Object> getJobPositionByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Job Position data retrieved successfully")
                .data(jobPositionService.getJobPositionByUuid(uuid))
                .build();
    }

    @GetMapping("/get-jobPosition-by-title/{title}")
    public ResponseTemplate<Object> getJobPositionByTitle(@PathVariable String title){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Job Position data retrieved successfully")
                .data(jobPositionService.getJobPositionByTitle(title))
                .build();
    }

    @GetMapping("/get-jobPosition-by-pagination/pagination")
    public ResponseTemplate<Object> getAllJobPositionByPagination(Pageable pageable){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Job Position data retrieved by pagination successfully")
                .data(jobPositionService.getAllJobPositionByPagination(pageable))
                .build();
    }

    @PostMapping("/create-jobPositon")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTemplate<Object> createJobPosition(@RequestBody @Validated JobPositionCreateDto createDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.CREATED.toString())
                .message("Job Position data has been created successfully")
                .data(jobPositionService.createJobPosition(createDto))
                .build();
    }

    @PutMapping("/update-jobPosition-by-uuid/{uuid}")
    public ResponseTemplate<Object> updateJobPositonByUuid(@PathVariable String uuid, @RequestBody @Validated JobPositionUpdateDto updateDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Job Position data has been updated successfully")
                .data(jobPositionService.updateJobPositonByUuid(uuid, updateDto))
                .build();
    }

    @DeleteMapping("/delete-jobPosition-by-uuid/{uuid}")
    public ResponseTemplate<Object> deleteJobPositionByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message(jobPositionService.deleteJobPositionByUuid(uuid))
                .data(null)
                .build();
    }
}
