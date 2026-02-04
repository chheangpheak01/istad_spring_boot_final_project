package com.sopheak.istadfinalems.service.employeDocument;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.EmployeeDocument;
import com.sopheak.istadfinalems.entities.emun.AuditAction;
import com.sopheak.istadfinalems.exception.EmployeeDocumentNotFoundException;
import com.sopheak.istadfinalems.exception.EmployeeNotFoundException;
import com.sopheak.istadfinalems.mapper.EmployeeDocumentMapStruct;
import com.sopheak.istadfinalems.model.dto.employee_document.EmployeeDocumentCreateDto;
import com.sopheak.istadfinalems.model.dto.employee_document.EmployeeDocumentResponseDto;
import com.sopheak.istadfinalems.model.dto.employee_document.EmployeeDocumentUpdateDto;
import com.sopheak.istadfinalems.repository.EmployeeDocumentRepository;
import com.sopheak.istadfinalems.repository.EmployeeRepository;
import com.sopheak.istadfinalems.service.audit.AuditService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeDocumentServiceImpl implements EmployeeDocumentService {

    private final EmployeeDocumentRepository employeeDocumentRepository;
    private final EmployeeDocumentMapStruct employeeDocumentMapStruct;
    private final AuditService auditService;
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDocumentResponseDto findDocumentByUuid(String uuid) {
        Optional<EmployeeDocument> employeeDocument = employeeDocumentRepository.findByUuidAndIsDeletedFalse(uuid);
        if (employeeDocument.isEmpty()) {
            throw new EmployeeDocumentNotFoundException("Employee document not found with this uuid: " + uuid);
        }
        return employeeDocumentMapStruct.mapEmployeeDocumentToEmployeeDocumentResponseDto(employeeDocument.get());
    }

    @Override
    public List<EmployeeDocumentResponseDto> findDocumentsByEmployeeUuid(String uuid) {
        List<EmployeeDocument> documents = employeeDocumentRepository.findByEmployeeUuidAndIsDeletedFalse(uuid);
        if (documents.isEmpty()) {
            throw new EmployeeDocumentNotFoundException("No active documents found for employee uuid: " + uuid);
        }
        return documents.stream()
                .map(employeeDocumentMapStruct::mapEmployeeDocumentToEmployeeDocumentResponseDto)
                .toList();
    }

    @Override
    public Page<EmployeeDocumentResponseDto> findAllDocuments(Pageable pageable) {
        Page<EmployeeDocument> employeeDocuments = employeeDocumentRepository.findAllByIsDeletedFalse(pageable);
        if (employeeDocuments.isEmpty()) {
            throw new EmployeeDocumentNotFoundException("No Employee document found for this page");
        }
        return employeeDocuments.map(employeeDocumentMapStruct::mapEmployeeDocumentToEmployeeDocumentResponseDto);
    }

    @Override
    @Transactional
    public EmployeeDocumentResponseDto createDocument(EmployeeDocumentCreateDto createDto) {
        Employee employee = employeeRepository.findEmployeeByUuidAndIsDeletedIsFalse(createDto.employeeUuids())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with UUID: " + createDto.employeeUuids()));
        EmployeeDocument document= new EmployeeDocument();

        document.setUuid(UUID.randomUUID().toString());
        document.setDocumentName(createDto.documentName());
        document.setDocumentType(createDto.documentType());
        document.setDownloadUrl(createDto.downloadUrl());
        document.setEmployee(employee);
        document.setIsDeleted(false);
        employeeDocumentRepository.save(document);

        EmployeeDocument savedDocument = employeeDocumentRepository.save(document);

        auditService.log(
                "EmployeeDocument",
                savedDocument.getId().toString(),
                AuditAction.CREATE,
                "Created employee document: " + savedDocument.getDocumentName()
        );
        return employeeDocumentMapStruct.mapEmployeeDocumentToEmployeeDocumentResponseDto(document);
    }

    @Override
    @Transactional
    public EmployeeDocumentResponseDto updateDocumentByUuid(String uuid, EmployeeDocumentUpdateDto updateDto) {
        EmployeeDocument document = employeeDocumentRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new EmployeeDocumentNotFoundException("Document not found with UUID: " + uuid));

        document.setDocumentName(updateDto.documentName());
        document.setDocumentType(updateDto.documentType());
        document.setIsDeleted(updateDto.isDeleted());

        EmployeeDocument updatedDoc = employeeDocumentRepository.save(document);

        auditService.log(
                "EmployeeDocument",
                updatedDoc.getId().toString(),
                AuditAction.UPDATE,
                "Updated details for document: " + updatedDoc.getDocumentName()
        );
        return employeeDocumentMapStruct.mapEmployeeDocumentToEmployeeDocumentResponseDto(updatedDoc);
    }

    @Override
    @Transactional
    public String deleteDocumentByUuid(String uuid) {
        EmployeeDocument document = employeeDocumentRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new EmployeeDocumentNotFoundException("Document not found with UUID: " + uuid));
        document.setIsDeleted(true);
        employeeDocumentRepository.save(document);
        auditService.log(
                "EmployeeDocument",
                document.getId().toString(),
                AuditAction.DELETE,
                "Soft deleted document: " + document.getDocumentName()
        );
        return "Employee document with UUID " + uuid + " has been deleted successfully";
    }
}
