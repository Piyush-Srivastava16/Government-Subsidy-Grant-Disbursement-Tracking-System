package com.government.subsidy.controller;

import com.government.subsidy.entity.Subsidy;
import com.government.subsidy.service.SubsidyService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController    // "@RestController marks the class as a REST controller that handles HTTP requests and returns data, usually in JSON format."
@RequestMapping("/api/subsidies")
public class SubsidyController {

    private final SubsidyService subsidyService;

    public SubsidyController(
            @Qualifier("subsidyServiceV2") SubsidyService subsidyService) {   // @Qualifier is used to select a specific bean when multiple beans of the same type are available.
        this.subsidyService = subsidyService;
    }

   @PostMapping     // @PostMapping maps HTTP POST requests to a controller method.
    public ResponseEntity<Subsidy> createSubsidy (@RequestBody Subsidy subsidy) {   //JSON → Java object

        Subsidy createdSubsidy = subsidyService.createSubsidy(subsidy);

        return new ResponseEntity<>(createdSubsidy, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subsidy> getSubsidyById(
            @PathVariable Long id) {  // URL value → Java variable

        Subsidy subsidy =
                subsidyService.getSubsidyById(id);

        return ResponseEntity.ok(subsidy);
    }

    @GetMapping
    public ResponseEntity<List<Subsidy>> getAllSubsidies() {

        List<Subsidy> subsidies = subsidyService.getAllSubsidies(); 

        return ResponseEntity.ok(subsidies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subsidy> updateSubsidy(
            @PathVariable Long id,
            @RequestBody Subsidy subsidy) {      //  @RequestBody maps the JSON request body to a Java object.

        Subsidy updatedSubsidy =
                subsidyService.updateSubsidy(id, subsidy);

        return ResponseEntity.ok(updatedSubsidy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubsidy(
            @PathVariable Long id) {

        subsidyService.deleteSubsidy(id);

        return ResponseEntity.noContent().build();
    }
}
