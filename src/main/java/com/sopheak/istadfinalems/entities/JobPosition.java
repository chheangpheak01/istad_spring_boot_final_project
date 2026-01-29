package com.sopheak.istadfinalems.entities;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "job_positions")
@Data
@EntityListeners(AuditingEntityListener.class)
public class JobPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(nullable = false, unique = true)
    private String title;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "jobPosition")
    private List<Employee> employees;
}
