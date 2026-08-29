package com.government.subsidy.service;

import com.government.subsidy.entity.BeneficiaryDocument;

import java.util.List;

public interface BeneficiaryDocumentService {

    BeneficiaryDocument createDocument(BeneficiaryDocument document);

    BeneficiaryDocument getDocumentById(Long id);

    List<BeneficiaryDocument> getAllDocuments();

    BeneficiaryDocument updateDocument(Long id, BeneficiaryDocument document);

    void deleteDocument(Long id);
}