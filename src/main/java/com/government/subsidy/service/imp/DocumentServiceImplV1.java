package com.government.subsidy.service.imp;

import com.government.subsidy.entity.Document;
import com.government.subsidy.repository.DocumentRepository;
import com.government.subsidy.service.DocumentService;
import org.springframework.stereotype.Service;
import com.government.subsidy.entity.Application;
import com.government.subsidy.repository.ApplicationRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

@Service("documentServiceV1")
public class DocumentServiceImplV1 implements DocumentService {

    private final DocumentRepository documentRepository;
    private final ApplicationRepository applicationRepository;

    public DocumentServiceImplV1(
            DocumentRepository documentRepository,
            ApplicationRepository applicationRepository) {

        this.documentRepository = documentRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Document createDocument(Document document) {

        if (document.getStatus() == null
                || document.getStatus().isBlank()) {

            document.setStatus("PENDING");
        }

        if (document.getUploadedAt() == null) {
            document.setUploadedAt(
                    LocalDateTime.now());
        }

        return documentRepository.save(document);
    }

    @Override
    public Document getDocumentById(Long id) {

        return documentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Document not found with id: " + id));
    }

    @Override
    public List<Document> getAllDocuments() {

        return documentRepository.findAll();
    }

    @Override
    public Document updateDocument(
            Long id,
            Document document) {

        Document existingDocument =
                documentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Document not found with id: " + id));

        if (document.getDocumentName() != null) {
            existingDocument.setDocumentName(
                    document.getDocumentName());
        }

        if (document.getDocumentType() != null) {
            existingDocument.setDocumentType(
                    document.getDocumentType());
        }

        if (document.getFileName() != null) {
            existingDocument.setFileName(
                    document.getFileName());
        }

        if (document.getFilePath() != null) {
            existingDocument.setFilePath(
                    document.getFilePath());
        }

        if (document.getStatus() != null) {
            existingDocument.setStatus(
                    document.getStatus().toUpperCase());
        }

        if (document.getApplication() != null) {
            existingDocument.setApplication(
                    document.getApplication());
        }

        return documentRepository.save(existingDocument);
    }

    @Override
    public Document uploadDocument(
            MultipartFile file,
            Long applicationId,
            String documentName,
            String documentType) {

        try {

            Application application =
                    applicationRepository.findById(applicationId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Application not found with id: "
                                                    + applicationId));

            if (file.isEmpty()) {
                throw new RuntimeException(
                        "Uploaded file is empty");
            }

            Path uploadDirectory =
                    Paths.get("uploads");

            Files.createDirectories(uploadDirectory);

            String originalFileName =
                    file.getOriginalFilename();

            String storedFileName =
                    UUID.randomUUID()
                            + "_"
                            + originalFileName;

            Path filePath =
                    uploadDirectory.resolve(
                            storedFileName);

            Files.copy(
                    file.getInputStream(),
                    filePath);

            Document document =
                    new Document();

            document.setDocumentName(
                    documentName);

            document.setDocumentType(
                    documentType);

            document.setFileName(
                    originalFileName);

            document.setFilePath(
                    filePath.toString());

            document.setStatus("PENDING");

            document.setUploadedAt(
                    java.time.LocalDateTime.now());

            document.setApplication(
                    application);

            return documentRepository.save(
                    document);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to upload document",
                    e);
        }
    }

    @Override
    public void deleteDocument(Long id) {

        if (!documentRepository.existsById(id)) {

            throw new RuntimeException(
                    "Document not found with id: " + id);
        }

        documentRepository.deleteById(id);
    }
}