 package com.government.subsidy.controller;

import com.government.subsidy.service.AnalyticsService;
import com.government.subsidy.service.ApplicationAnalyticsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    private final ApplicationAnalyticsService
            applicationAnalyticsService;

    public AnalyticsController(
            @Qualifier("analyticsServiceV1")
            AnalyticsService analyticsService,

            @Qualifier("applicationAnalyticsServiceV1")
            ApplicationAnalyticsService applicationAnalyticsService) {

        this.analyticsService = analyticsService;
        this.applicationAnalyticsService =
                applicationAnalyticsService;
    }

    @GetMapping("/beneficiaries-by-region")
    public ResponseEntity<Map<String, Long>>
    getBeneficiaryCountByRegion() {

        return ResponseEntity.ok(
                analyticsService.getBeneficiaryCountByRegion());
    }

    // Region-wise Released Fund Analytics
    @GetMapping("/released-fund-by-region")
    public ResponseEntity<Map<String, Double>>
    getReleasedFundByRegion() {

        return ResponseEntity.ok(
                analyticsService.getReleasedFundByRegion());
    }

    @GetMapping("/total-applications")
    public ResponseEntity<Long> getTotalApplications() {

        return ResponseEntity.ok(
                analyticsService.getTotalApplications());
    }

    @GetMapping("/approved-applications")
    public ResponseEntity<Long> getApprovedApplications() {

        return ResponseEntity.ok(
                analyticsService.getApprovedApplications());
    }

    @GetMapping("/pending-applications")
    public ResponseEntity<Long> getPendingApplications() {

        return ResponseEntity.ok(
                analyticsService.getPendingApplications());
    }

    @GetMapping("/total-fund-released")
    public ResponseEntity<Double> getTotalFundReleased() {

        return ResponseEntity.ok(
                analyticsService.getTotalFundReleased());
    }

    @GetMapping("/total-fund-utilized")
    public ResponseEntity<Double> getTotalFundUtilized() {

        return ResponseEntity.ok(
                analyticsService.getTotalFundUtilized());
    }

    // Application Status Analytics
    @GetMapping("/applications-by-status")
    public ResponseEntity<Map<String, Long>>
    getApplicationCountByStatus() {

        return ResponseEntity.ok(
                applicationAnalyticsService
                        .getApplicationCountByStatus());
    }
}
