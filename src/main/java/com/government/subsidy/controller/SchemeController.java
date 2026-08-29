package com.government.subsidy.controller;

import com.government.subsidy.entity.Scheme;
import com.government.subsidy.service.SchemeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schemes")
public class SchemeController {

    private final SchemeService schemeService;

    public SchemeController(
            @Qualifier("schemeServiceV1")
            SchemeService schemeService) {

        this.schemeService = schemeService;
    }

    @PostMapping
    public ResponseEntity<Scheme> createScheme(
            @RequestBody Scheme scheme) {

        return ResponseEntity.ok(
                schemeService.createScheme(scheme));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scheme> getSchemeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                schemeService.getSchemeById(id));
    }

    @GetMapping
    public ResponseEntity<List<Scheme>> getAllSchemes() {

        return ResponseEntity.ok(
                schemeService.getAllSchemes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Scheme> updateScheme(
            @PathVariable Long id,
            @RequestBody Scheme scheme) {

        return ResponseEntity.ok(
                schemeService.updateScheme(id, scheme));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheme(
            @PathVariable Long id) {

        schemeService.deleteScheme(id);
        return ResponseEntity.noContent().build();
    }
}