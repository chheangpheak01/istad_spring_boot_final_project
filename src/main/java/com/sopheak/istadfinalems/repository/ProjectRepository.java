package com.sopheak.istadfinalems.repository;
import com.sopheak.istadfinalems.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findAllByUuidIn(List<String> uuids);
    Optional<Project> findByUuidAndIsDeletedFalse(String uuid);
    Page<Project> findAllByIsDeletedFalse(Pageable pageable);
    boolean existsByProjectNameAndIsDeletedFalse(String projectName);
}
