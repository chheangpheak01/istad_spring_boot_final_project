package com.sopheak.istadfinalems.repository;
import com.sopheak.istadfinalems.entities.EmployeeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Integer> {
    List<EmployeeDocument> findAllByUuidIn(List<String> uuids);
}
