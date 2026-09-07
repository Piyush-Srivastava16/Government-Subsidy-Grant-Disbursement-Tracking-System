package com.government.subsidy.controller;

import com.government.subsidy.entity.Disbursement;
import com.government.subsidy.service.DisbursementService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disbursements")
public class DisbursementController {

    private final DisbursementService disbursementService;

    public DisbursementController(
            @Qualifier("disbursementServiceV1")
            DisbursementService disbursementService) {

        this.disbursementService = disbursementService;
    }

    @PostMapping
    public ResponseEntity<Disbursement> createDisbursement(
            @RequestBody Disbursement disbursement) {

        return ResponseEntity.ok(
                disbursementService.createDisbursement(disbursement));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disbursement> getDisbursementById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                disbursementService.getDisbursementById(id));
    }

    @GetMapping
    public ResponseEntity<List<Disbursement>> getAllDisbursements() {

        return ResponseEntity.ok(
                disbursementService.getAllDisbursements());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disbursement> updateDisbursement(
            @PathVariable Long id,
            @RequestBody Disbursement disbursement) {

        return ResponseEntity.ok(
                disbursementService.updateDisbursement(id, disbursement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisbursement(
            @PathVariable Long id) {

        disbursementService.deleteDisbursement(id);

        return ResponseEntity.noContent().build();
    }
}