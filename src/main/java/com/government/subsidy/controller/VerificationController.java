package com.government.subsidy.controller;

import com.government.subsidy.entity.Verification;
import com.government.subsidy.service.VerificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/verifications")
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(
            @Qualifier("verificationServiceV1")
            VerificationService verificationService) {

        this.verificationService = verificationService;
    }

    @PostMapping
    public ResponseEntity<Verification> createVerification(
            @RequestBody Verification verification) {

        return ResponseEntity.ok(
                verificationService.createVerification(verification));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Verification> getVerificationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                verificationService.getVerificationById(id));
    }

    @GetMapping
    public ResponseEntity<List<Verification>> getAllVerifications() {

        return ResponseEntity.ok(
                verificationService.getAllVerifications());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Verification> updateVerification(
            @PathVariable Long id,
            @RequestBody Verification verification) {

        return ResponseEntity.ok(
                verificationService.updateVerification(id, verification));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerification(
            @PathVariable Long id) {

        verificationService.deleteVerification(id);

        return ResponseEntity.noContent().build();
    }
}