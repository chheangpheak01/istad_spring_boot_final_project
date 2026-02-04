package com.sopheak.istadfinalems.repository;
import com.sopheak.istadfinalems.entities.Department;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.JobPosition;
import com.sopheak.istadfinalems.entities.emun.EmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findEmployeeByUuidAndIsDeletedIsFalse(String uuid);
    Optional<Employee> findEmployeeByNameAndIsDeletedIsFalse(String name);
    Optional<Employee> findEmployeeByPhoneNumberAndIsDeletedIsFalse(String phoneNumber);
    List<Employee> findAllByDepartment(Department department);
    List<Employee> findAllByJobPosition(JobPosition jobPosition);
    Page<Employee> findAllEmployeeByIsDeletedIsFalse(Pageable pageable);
    Page<Employee> findEmployeeByNameContainingIgnoreCaseAndIsDeletedFalse(String name, Pageable pageable);
    Boolean existsEmployeesByEmail(String email);
    Boolean existsEmployeesByPhoneNumber(String phoneNumber);
    List<Employee> findEmployeeByDepartmentNameAndJobPositionTitleAndStatus(String departmentName, String jobTitle, EmployeeStatus status);
    List<Employee> findEmployeeByDepartmentNameAndJobPositionTitle(String departmentName, String jobTitle);
    List<Employee> findEmployeeByDepartmentName(String departmentName);
    List<Employee> findEmployeeByJobPositionTitle(String jobTitle);
    List<Employee> findEmployeeByStatus(EmployeeStatus status);
}