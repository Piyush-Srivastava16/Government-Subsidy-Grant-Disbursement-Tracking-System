package com.government.subsidy.controller;

import com.government.subsidy.dto.DashboardSummary;
import com.government.subsidy.service.DashboardService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            @Qualifier("dashboardServiceV1")
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public DashboardSummary getDashboardSummary() {

        return dashboardService.getDashboardSummary();
    }
}

