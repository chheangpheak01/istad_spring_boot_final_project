package com.sopheak.istadfinalems.repository;
import com.sopheak.istadfinalems.entities.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Integer> {
    Optional<JobPosition> findByUuid(String uuid);
}
