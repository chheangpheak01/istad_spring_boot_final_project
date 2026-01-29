package com.sopheak.istadfinalems.entities;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "projects")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(nullable = false, unique = true)
    private String projectName;
    private String description;
    @Column(nullable = false)
    private Boolean isDeleted;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees = new HashSet<>();
}