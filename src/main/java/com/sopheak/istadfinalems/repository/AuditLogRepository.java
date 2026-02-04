package com.sopheak.istadfinalems.repository;
import com.sopheak.istadfinalems.entities.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {
    Page<AuditLog> findAllByOrderByTimestampDesc(Pageable pageable);
}
