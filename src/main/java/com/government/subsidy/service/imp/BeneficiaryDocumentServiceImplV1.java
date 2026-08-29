package com.government.subsidy.service.imp;

import com.government.subsidy.entity.BeneficiaryDocument;
import com.government.subsidy.repository.BeneficiaryDocumentRepository;
import com.government.subsidy.service.BeneficiaryDocumentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("beneficiaryDocumentServiceV1")
public class BeneficiaryDocumentServiceImplV1
        implements BeneficiaryDocumentService {

    private final BeneficiaryDocumentRepository documentRepository;

    public BeneficiaryDocumentServiceImplV1(
            BeneficiaryDocumentRepository documentRepository) {

        this.documentRepository = documentRepository;
    }

    @Override
    public BeneficiaryDocument createDocument(
            BeneficiaryDocument document) {

        return documentRepository.save(document);
    }

    @Override
    public BeneficiaryDocument getDocumentById(Long id) {

        return documentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Document not found with id: " + id));
    }

    @Override
    public List<BeneficiaryDocument> getAllDocuments() {

        return documentRepository.findAll();
    }

    @Override
    public BeneficiaryDocument updateDocument(
            Long id,
            BeneficiaryDocument document) {

        BeneficiaryDocument existingDocument =
                documentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Document not found with id: " + id));

        existingDocument.setDocumentType(
                document.getDocumentType());

        existingDocument.setDocumentName(
                document.getDocumentName());

        existingDocument.setDocumentNumber(
                document.getDocumentNumber());

        existingDocument.setVerificationStatus(
                document.getVerificationStatus());

        existingDocument.setBeneficiary(
                document.getBeneficiary());

        return documentRepository.save(existingDocument);
    }

    @Override
    public void deleteDocument(Long id) {

        documentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Document not found with id: " + id));

        documentRepository.deleteById(id);
    }
}