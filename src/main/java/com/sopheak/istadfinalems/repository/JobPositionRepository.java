package com.sopheak.istadfinalems.repository;
import com.sopheak.istadfinalems.entities.JobPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Integer> {
    Optional<JobPosition> findByUuidAndIsDeletedFalse(String uuid);
    Optional<JobPosition> findByTitleAndIsDeletedFalse(String title);
    Page<JobPosition> findAllByIsDeletedFalse(Pageable pageable);
    Boolean existsByTitleAndIsDeletedFalse(String title);
}