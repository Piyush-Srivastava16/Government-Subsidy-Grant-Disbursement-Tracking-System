package com.government.subsidy.controller;

import com.government.subsidy.entity.BeneficiaryDocument;
import com.government.subsidy.service.BeneficiaryDocumentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class BeneficiaryDocumentController {

    private final BeneficiaryDocumentService documentService;

    public BeneficiaryDocumentController(
            @Qualifier("beneficiaryDocumentServiceV1")
            BeneficiaryDocumentService documentService) {

        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<BeneficiaryDocument> createDocument(
            @RequestBody BeneficiaryDocument document) {

        return ResponseEntity.ok(
                documentService.createDocument(document));
    }

    @PostMapping("/upload")
    public ResponseEntity<BeneficiaryDocument> uploadDocument(

            @RequestParam("file")
            MultipartFile file,

            @RequestParam("beneficiaryId")
            Long beneficiaryId,

            @RequestParam("documentName")
            String documentName,

            @RequestParam("documentType")
            String documentType,

            @RequestParam("documentNumber")
            String documentNumber) {

        return ResponseEntity.ok(
                documentService.uploadDocument(
                        file,
                        beneficiaryId,
                        documentName,
                        documentType,
                        documentNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeneficiaryDocument> getDocumentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                documentService.getDocumentById(id));
    }

    @GetMapping
    public ResponseEntity<List<BeneficiaryDocument>> getAllDocuments() {

        return ResponseEntity.ok(
                documentService.getAllDocuments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeneficiaryDocument> updateDocument(
            @PathVariable Long id,
            @RequestBody BeneficiaryDocument document) {

        return ResponseEntity.ok(
                documentService.updateDocument(id, document));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable Long id) {

        documentService.deleteDocument(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<BeneficiaryDocument> verifyDocument(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(
                documentService.verifyDocument(id, status));
    }
}