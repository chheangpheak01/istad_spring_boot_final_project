package com.sopheak.istadfinalems.service;
import com.sopheak.istadfinalems.model.dto.employee_document.EmployeeDocumentCreateDto;
import com.sopheak.istadfinalems.model.dto.employee_document.EmployeeDocumentResponseDto;
import com.sopheak.istadfinalems.model.dto.employee_document.EmployeeDocumentUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface EmployeeDocumentService {
    EmployeeDocumentResponseDto findDocumentByUuid(String uuid);
    List<EmployeeDocumentResponseDto> findDocumentsByEmployeeUuid(String uuid);
    Page<EmployeeDocumentResponseDto> findAllDocuments(Pageable pageable);
    EmployeeDocumentResponseDto createDocument(EmployeeDocumentCreateDto createDto);
    EmployeeDocumentResponseDto updateDocumentByUuid(String uuid, EmployeeDocumentUpdateDto updateDto);
    String deleteDocumentByUuid(String uuid);
}
