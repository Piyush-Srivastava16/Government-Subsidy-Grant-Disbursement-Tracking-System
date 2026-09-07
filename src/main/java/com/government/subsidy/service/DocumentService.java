package com.government.subsidy.service;

import com.government.subsidy.entity.Document;

import java.util.List;

public interface DocumentService {

    Document createDocument(Document document);

    Document getDocumentById(Long id);

    List<Document> getAllDocuments();

    Document updateDocument(
            Long id,
            Document document);

    Document uploadDocument(
            org.springframework.web.multipart.MultipartFile file,
            Long applicationId,
            String documentName,
            String documentType);

    void deleteDocument(Long id);
}