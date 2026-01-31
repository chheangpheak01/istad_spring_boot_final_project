package com.sopheak.istadfinalems.repository;
import com.sopheak.istadfinalems.entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Optional<Department> findByUuidAndIsDeletedFalse(String uuid);
    Optional<Department> findByNameAndIsDeletedFalse(String name);
    Page<Department> findAllByIsDeletedFalse(Pageable pageable);
    Boolean existsByNameAndIsDeletedFalse(String name);
}
