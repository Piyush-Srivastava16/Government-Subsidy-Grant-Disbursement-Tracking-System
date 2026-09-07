package com.government.subsidy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String milestoneName;

    @Setter
    private LocalDate dueDate;

    @Setter
    private String status;

    @Setter
    private String remarks;

    @Setter
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

}