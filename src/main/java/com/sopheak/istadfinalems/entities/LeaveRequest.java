package com.sopheak.istadfinalems.entities;
import com.sopheak.istadfinalems.entities.emun.LeaveStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_requests")
@Data
@EntityListeners(AuditingEntityListener.class)
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Column(nullable = false)
    private String reason;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LeaveStatus status;
    @Column(nullable = false)
    private Boolean isDeleted;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime submittedAt;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}