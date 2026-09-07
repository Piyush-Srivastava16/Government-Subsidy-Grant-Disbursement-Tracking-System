package com.government.subsidy.controller;

import com.government.subsidy.entity.Milestone;
import com.government.subsidy.service.MilestoneService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {

    private final MilestoneService milestoneService;

    public MilestoneController(
            @Qualifier("milestoneServiceV1")
            MilestoneService milestoneService) {

        this.milestoneService = milestoneService;
    }

    @PostMapping
    public ResponseEntity<Milestone> createMilestone(
            @RequestBody Milestone milestone) {

        return ResponseEntity.ok(
                milestoneService.createMilestone(milestone));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Milestone> getMilestoneById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                milestoneService.getMilestoneById(id));
    }

    @GetMapping
    public ResponseEntity<List<Milestone>> getAllMilestones() {

        return ResponseEntity.ok(
                milestoneService.getAllMilestones());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Milestone> updateMilestone(
            @PathVariable Long id,
            @RequestBody Milestone milestone) {

        return ResponseEntity.ok(
                milestoneService.updateMilestone(id, milestone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilestone(
            @PathVariable Long id) {

        milestoneService.deleteMilestone(id);

        return ResponseEntity.noContent().build();
    }
}