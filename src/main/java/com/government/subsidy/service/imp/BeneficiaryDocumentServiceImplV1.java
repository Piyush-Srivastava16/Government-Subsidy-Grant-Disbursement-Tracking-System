package com.government.subsidy.service.imp;

import com.government.subsidy.entity.Beneficiary;
import com.government.subsidy.entity.BeneficiaryDocument;
import com.government.subsidy.repository.BeneficiaryDocumentRepository;
import com.government.subsidy.repository.BeneficiaryRepository;
import com.government.subsidy.service.BeneficiaryDocumentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service("beneficiaryDocumentServiceV1")
public class BeneficiaryDocumentServiceImplV1
        implements BeneficiaryDocumentService {

    private final BeneficiaryDocumentRepository documentRepository;
    private final BeneficiaryRepository beneficiaryRepository;

    public BeneficiaryDocumentServiceImplV1(
            BeneficiaryDocumentRepository documentRepository,
            BeneficiaryRepository beneficiaryRepository) {

        this.documentRepository = documentRepository;
        this.beneficiaryRepository = beneficiaryRepository;
    }

    @Override
    public BeneficiaryDocument createDocument(
            BeneficiaryDocument document) {

        if (document.getVerificationStatus() == null
                || document.getVerificationStatus().isBlank()) {

            document.setVerificationStatus("PENDING");
        }

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

        if (document.getVerificationStatus() != null) {
            existingDocument.setVerificationStatus(
                    document.getVerificationStatus());
        }

        if (document.getBeneficiary() != null) {
            existingDocument.setBeneficiary(
                    document.getBeneficiary());
        }

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

    @Override
    public BeneficiaryDocument verifyDocument(
            Long id,
            String status) {

        BeneficiaryDocument document =
                documentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Document not found with id: " + id));

        if (!status.equalsIgnoreCase("VERIFIED")
                && !status.equalsIgnoreCase("REJECTED")) {

            throw new RuntimeException(
                    "Invalid verification status. Use VERIFIED or REJECTED");
        }

        document.setVerificationStatus(
                status.toUpperCase());

        return documentRepository.save(document);
    }

    @Override
    public BeneficiaryDocument uploadDocument(
            MultipartFile file,
            Long beneficiaryId,
            String documentName,
            String documentType,
            String documentNumber) {

        try {

            if (file == null || file.isEmpty()) {
                throw new RuntimeException(
                        "Please select a file to upload");
            }

            Beneficiary beneficiary =
                    beneficiaryRepository.findById(beneficiaryId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Beneficiary not found with id: "
                                                    + beneficiaryId));

            // Create uploads directory
            Path uploadDirectory =
                    Paths.get("uploads");

            Files.createDirectories(uploadDirectory);

            String originalFileName =
                    file.getOriginalFilename();

            if (originalFileName == null
                    || originalFileName.isBlank()) {

                throw new RuntimeException(
                        "Invalid file name");
            }

            // Unique name prevents overwriting existing files
            String storedFileName =
                    UUID.randomUUID()
                            + "_"
                            + originalFileName;

            Path targetPath =
                    uploadDirectory.resolve(
                            storedFileName);

            Files.copy(
                    file.getInputStream(),
                    targetPath);

            BeneficiaryDocument document =
                    new BeneficiaryDocument();

            document.setDocumentName(documentName);
            document.setDocumentType(documentType);
            document.setDocumentNumber(documentNumber);
            document.setVerificationStatus("PENDING");
            document.setFileName(originalFileName);
            document.setFilePath(
                    targetPath.toString());
            document.setBeneficiary(beneficiary);

            return documentRepository.save(document);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to store uploaded file",
                    e);
        }
    }
}