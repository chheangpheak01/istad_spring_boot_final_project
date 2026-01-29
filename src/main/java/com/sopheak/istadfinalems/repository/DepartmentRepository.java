package com.sopheak.istadfinalems.repository;
import com.sopheak.istadfinalems.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Optional<Department> findByUuid(String uuid);
}
