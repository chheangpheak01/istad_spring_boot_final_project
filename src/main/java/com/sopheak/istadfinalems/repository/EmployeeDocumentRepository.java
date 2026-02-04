package com.sopheak.istadfinalems.repository;
import com.sopheak.istadfinalems.entities.EmployeeDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Integer> {
    List<EmployeeDocument> findAllByUuidIn(List<String> uuids);
    Optional<EmployeeDocument> findByUuidAndIsDeletedFalse(String uuid);
    List<EmployeeDocument> findByEmployeeUuidAndIsDeletedFalse(String uuid);
    Page<EmployeeDocument> findAllByIsDeletedFalse(Pageable pageable);
}
