package com.sopheak.istadfinalems.service.audit;
import com.sopheak.istadfinalems.entities.emun.AuditAction;
import org.springframework.stereotype.Service;

@Service
public interface AuditService {
    void log(String entityName, String entityId, AuditAction action, String details);
}
