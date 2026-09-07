package com.government.subsidy.service.imp;

import com.government.subsidy.entity.AuditLog;
import com.government.subsidy.repository.AuditLogRepository;
import com.government.subsidy.service.AuditLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("auditLogServiceV1")
public class AuditLogServiceImplV1
        implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImplV1(
            AuditLogRepository auditLogRepository) {

        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public AuditLog createAuditLog(
            String action,
            String entityName,
            Long entityId,
            String performedBy) {

        AuditLog auditLog = new AuditLog();

        auditLog.setAction(action);
        auditLog.setEntityName(entityName);
        auditLog.setEntityId(entityId);
        auditLog.setPerformedBy(performedBy);

        // Automatically store current date and time
        auditLog.setTimestamp(LocalDateTime.now());

        return auditLogRepository.save(auditLog);
    }

    @Override
    public List<AuditLog> getAllAuditLogs() {

        return auditLogRepository.findAll();
    }
}