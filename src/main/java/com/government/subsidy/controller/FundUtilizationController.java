 package com.government.subsidy.controller;

import com.government.subsidy.dto.FundUtilizationSummary;
import com.government.subsidy.entity.FundUtilization;
import com.government.subsidy.service.FundUtilizationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fund-utilizations")
public class FundUtilizationController {

    private final FundUtilizationService fundUtilizationService;

    public FundUtilizationController(
            @Qualifier("fundUtilizationServiceV1")
            FundUtilizationService fundUtilizationService) {

        this.fundUtilizationService =
                fundUtilizationService;
    }

    @PostMapping
    public ResponseEntity<FundUtilization> createFundUtilization(
            @RequestBody FundUtilization fundUtilization) {

        return ResponseEntity.ok(
                fundUtilizationService
                        .createFundUtilization(fundUtilization));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FundUtilization> getFundUtilizationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                fundUtilizationService
                        .getFundUtilizationById(id));
    }

    @GetMapping
    public ResponseEntity<List<FundUtilization>> getAllFundUtilizations() {

        return ResponseEntity.ok(
                fundUtilizationService
                        .getAllFundUtilizations());
    }

    // Fund Utilization Analytics Summary
    @GetMapping("/summary")
    public ResponseEntity<FundUtilizationSummary>
    getFundUtilizationSummary() {

        return ResponseEntity.ok(
                fundUtilizationService
                        .getFundUtilizationSummary());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FundUtilization> updateFundUtilization(
            @PathVariable Long id,
            @RequestBody FundUtilization fundUtilization) {

        return ResponseEntity.ok(
                fundUtilizationService
                        .updateFundUtilization(id, fundUtilization));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFundUtilization(
            @PathVariable Long id) {

        fundUtilizationService.deleteFundUtilization(id);

        return ResponseEntity.noContent().build();
    }
}
