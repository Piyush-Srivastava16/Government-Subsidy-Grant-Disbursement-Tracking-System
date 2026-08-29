package com.government.subsidy.controller;

import com.government.subsidy.entity.Beneficiary;
import com.government.subsidy.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    public BeneficiaryController(
            @Qualifier("beneficiaryServiceV1")
            BeneficiaryService beneficiaryService) {

        this.beneficiaryService = beneficiaryService;
    }

    @PostMapping
    public ResponseEntity<Beneficiary> createBeneficiary(
            @RequestBody Beneficiary beneficiary) {

        return ResponseEntity.ok(
                beneficiaryService.createBeneficiary(beneficiary));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficiary> getBeneficiaryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                beneficiaryService.getBeneficiaryById(id));
    }

    @GetMapping
    public ResponseEntity<List<Beneficiary>> getAllBeneficiaries() {

        return ResponseEntity.ok(
                beneficiaryService.getAllBeneficiaries());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beneficiary> updateBeneficiary(
            @PathVariable Long id,
            @RequestBody Beneficiary beneficiary) {

        return ResponseEntity.ok(
                beneficiaryService.updateBeneficiary(id, beneficiary));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeneficiary(
            @PathVariable Long id) {

        beneficiaryService.deleteBeneficiary(id);
        return ResponseEntity.noContent().build();
    }
}