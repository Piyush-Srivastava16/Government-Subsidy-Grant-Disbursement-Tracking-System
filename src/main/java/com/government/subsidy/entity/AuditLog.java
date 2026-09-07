package com.government.subsidy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String action;

    @Setter
    private String entityName;

    @Setter
    private Long entityId;

    @Setter
    private String performedBy;

    @Setter
    private LocalDateTime timestamp;

}