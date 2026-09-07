package com.government.subsidy.service;

import com.government.subsidy.entity.AuditLog;

import java.util.List;

public interface AuditLogService {

    AuditLog createAuditLog(
            String action,
            String entityName,
            Long entityId,
            String performedBy);

    List<AuditLog> getAllAuditLogs();
}