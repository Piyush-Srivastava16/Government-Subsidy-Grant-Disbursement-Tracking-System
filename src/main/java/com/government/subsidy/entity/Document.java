package com.government.subsidy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String documentName;

    @Setter
    private String documentType;

    @Setter
    private String fileName;

    @Setter
    private String filePath;

    @Setter
    private String status;

    @Setter
    private LocalDateTime uploadedAt;

    @Setter
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;
}