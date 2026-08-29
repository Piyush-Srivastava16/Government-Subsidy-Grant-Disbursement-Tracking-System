package com.government.subsidy.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity       //Tells JPA that this Java class should be mapped to a database table....

public class Subsidy {

    @Id      //defines the primary key of the entity and uniquely identifies each record.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // @GeneratedValue is used to automatically generate the primary key value.
    private Long id;

    private String name;

    private String description;

    private Double amount;

    private String beneficiaryName;

    private String status;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(Double amount) {
//        this.amount = amount;
//    }
//
//    public String getBeneficiaryName() {
//        return beneficiaryName;
//    }
//
//    public void setBeneficiaryName(String beneficiaryName) {
//        this.beneficiaryName = beneficiaryName;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
}
