package com.sopheak.istadfinalems.repository;
import com.sopheak.istadfinalems.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findAllByUuidIn(List<String> uuids);
}
