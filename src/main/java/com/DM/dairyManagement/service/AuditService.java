package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.AuditLog;
import com.DM.dairyManagement.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuditService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuditService.class);
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    /**
     * Log an audit event asynchronously (non-blocking)
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAudit(String username, String action, String entityType, Long entityId, String details) {
        try {
            AuditLog auditLog = new AuditLog(username, action, entityType, entityId, details);
            auditLogRepository.save(auditLog);
            logger.debug("Audit log saved: {} - {} - {}", username, action, entityType);
        } catch (Exception e) {
            logger.error("Failed to save audit log: {}", e.getMessage());
            // Don't throw - audit logging shouldn't break the main flow
        }
    }
    
    /**
     * Log with severity level
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAudit(String username, String action, String entityType, Long entityId, 
                         String details, AuditLog.Severity severity) {
        try {
            AuditLog auditLog = new AuditLog(username, action, entityType, entityId, details);
            auditLog.setSeverity(severity);
            auditLogRepository.save(auditLog);
        } catch (Exception e) {
            logger.error("Failed to save audit log: {}", e.getMessage());
        }
    }
    
    /**
     * Log with IP address and user agent
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAudit(String username, String action, String entityType, Long entityId, 
                         String details, String ipAddress, String userAgent) {
        try {
            AuditLog auditLog = new AuditLog(username, action, entityType, entityId, details);
            auditLog.setIpAddress(ipAddress);
            auditLog.setUserAgent(userAgent);
            auditLogRepository.save(auditLog);
        } catch (Exception e) {
            logger.error("Failed to save audit log: {}", e.getMessage());
        }
    }
    
    // Query methods
    
    public Page<AuditLog> getAuditLogsByUser(String username, Pageable pageable) {
        return auditLogRepository.findByUsername(username, pageable);
    }
    
    public Page<AuditLog> getAuditLogsByAction(String action, Pageable pageable) {
        return auditLogRepository.findByAction(action, pageable);
    }
    
    public Page<AuditLog> getAuditLogsByEntity(String entityType, Long entityId, Pageable pageable) {
        return auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId, pageable);
    }
    
    public Page<AuditLog> getAuditLogsByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return auditLogRepository.findByTimestampBetween(start, end, pageable);
    }
    
    public Page<AuditLog> getAuditLogsBySeverity(AuditLog.Severity severity, Pageable pageable) {
        return auditLogRepository.findBySeverity(severity, pageable);
    }
    
    public Page<AuditLog> getAllAuditLogs(Pageable pageable) {
        return auditLogRepository.findAll(pageable);
    }
}
