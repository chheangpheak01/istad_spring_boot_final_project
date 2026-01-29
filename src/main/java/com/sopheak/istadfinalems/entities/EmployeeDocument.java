package com.sopheak.istadfinalems.entities;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_documents")
@Data
@EntityListeners(AuditingEntityListener.class)
public class EmployeeDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;

    private String documentName;
    private String documentType;
    private String downloadUrl;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @Column(nullable = false)
    private Boolean isDeleted;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}