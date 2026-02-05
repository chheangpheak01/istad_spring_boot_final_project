package com.sopheak.istadfinalems.service.impl;
import com.sopheak.istadfinalems.entities.AuditLog;
import com.sopheak.istadfinalems.entities.emun.AuditAction;
import com.sopheak.istadfinalems.repository.AuditLogRepository;
import com.sopheak.istadfinalems.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    final private AuditLogRepository auditLogRepository;

    @Override
    public void log(String entityName, String entityId, AuditAction action, String details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUuid(UUID.randomUUID().toString());
        auditLog.setEntityId(entityId);
        auditLog.setAction(action);
        auditLog.setEntityName(entityName);
        auditLog.setPerformedBy("ADMIN");
        auditLog.setIsDeleted(false);
        auditLog.setDetails(details);
        auditLogRepository.save(auditLog);
    }
}
