package com.sopheak.istadfinalems.entities;
import com.sopheak.istadfinalems.entities.emun.AuditAction;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;

    private String entityId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditAction action;
    @Column(nullable = false)
    private String entityName;
    private String performedBy;
    @Column(nullable = false)
    private Boolean isDeleted;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime timestamp;
    private String details;
}
