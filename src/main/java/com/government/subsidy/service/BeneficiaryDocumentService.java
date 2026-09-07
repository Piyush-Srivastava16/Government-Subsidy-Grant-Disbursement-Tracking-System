package com.government.subsidy.service;

import com.government.subsidy.entity.BeneficiaryDocument;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BeneficiaryDocumentService {

    BeneficiaryDocument createDocument(
            BeneficiaryDocument document);

    BeneficiaryDocument getDocumentById(Long id);

    List<BeneficiaryDocument> getAllDocuments();

    BeneficiaryDocument updateDocument(
            Long id,
            BeneficiaryDocument document);

    void deleteDocument(Long id);

    BeneficiaryDocument verifyDocument(
            Long id,
            String status);

    BeneficiaryDocument uploadDocument(
            MultipartFile file,
            Long beneficiaryId,
            String documentName,
            String documentType,
            String documentNumber);
}