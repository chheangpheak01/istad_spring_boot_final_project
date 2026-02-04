package com.sopheak.istadfinalems.repository;
import com.sopheak.istadfinalems.entities.LeaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
    Optional<LeaveRequest> findByUuidAndIsDeletedFalse(String uuid);
    Page<LeaveRequest> findAllByIsDeletedFalse(Pageable pageable);
}
