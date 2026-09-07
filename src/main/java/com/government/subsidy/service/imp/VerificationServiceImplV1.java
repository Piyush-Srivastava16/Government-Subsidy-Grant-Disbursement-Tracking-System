
package com.government.subsidy.service.imp;

import com.government.subsidy.entity.Application;
import com.government.subsidy.entity.Verification;
import com.government.subsidy.repository.ApplicationRepository;
import com.government.subsidy.repository.VerificationRepository;
import com.government.subsidy.service.AuditLogService;
import com.government.subsidy.service.VerificationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("verificationServiceV1")
public class VerificationServiceImplV1 implements VerificationService {

    private final VerificationRepository verificationRepository;
    private final ApplicationRepository applicationRepository;
    private final AuditLogService auditLogService;

    public VerificationServiceImplV1(
            VerificationRepository verificationRepository,
            ApplicationRepository applicationRepository,
            AuditLogService auditLogService) {

        this.verificationRepository = verificationRepository;
        this.applicationRepository = applicationRepository;
        this.auditLogService = auditLogService;
    }

    @Override
    public Verification createVerification(Verification verification) {

        if (verification.getStatus() == null
                || verification.getStatus().isBlank()) {

            verification.setStatus("PENDING");
        }

        Verification savedVerification =
                verificationRepository.save(verification);

        auditLogService.createAuditLog(
                "CREATE",
                "VERIFICATION",
                savedVerification.getId(),
                getCurrentUsername());

        return savedVerification;
    }

    @Override
    public Verification getVerificationById(Long id) {

        return verificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Verification not found with id: " + id));
    }

    @Override
    public List<Verification> getAllVerifications() {

        return verificationRepository.findAll();
    }

    @Override
    public Verification updateVerification(
            Long id,
            Verification verification) {

        Verification existingVerification =
                verificationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Verification not found with id: " + id));

        if (verification.getStatus() != null) {
            existingVerification.setStatus(
                    verification.getStatus().toUpperCase());
        }

        if (verification.getRemarks() != null) {
            existingVerification.setRemarks(
                    verification.getRemarks());
        }

        Verification updatedVerification =
                verificationRepository.save(existingVerification);

        auditLogService.createAuditLog(
                "UPDATE",
                "VERIFICATION",
                updatedVerification.getId(),
                getCurrentUsername());

        // Automatic workflow progression
        if ("APPROVED".equalsIgnoreCase(
                updatedVerification.getStatus())) {

            Application application =
                    updatedVerification.getApplication();

            String currentLevel =
                    updatedVerification.getVerificationLevel();

            if ("FIELD_OFFICER".equalsIgnoreCase(currentLevel)) {

                createNextVerification(
                        application,
                        "DISTRICT_OFFICER");

            } else if ("DISTRICT_OFFICER".equalsIgnoreCase(currentLevel)) {

                createNextVerification(
                        application,
                        "FINANCE_APPROVER");

            } else if ("FINANCE_APPROVER".equalsIgnoreCase(currentLevel)) {

                application.setStatus("APPROVED");

                applicationRepository.save(application);
            }
        }

        return updatedVerification;
    }

    @Override
    public void deleteVerification(Long id) {

        if (!verificationRepository.existsById(id)) {

            throw new RuntimeException(
                    "Verification not found with id: " + id);
        }

        auditLogService.createAuditLog(
                "DELETE",
                "VERIFICATION",
                id,
                getCurrentUsername());

        verificationRepository.deleteById(id);
    }

    private void createNextVerification(
            Application application,
            String nextLevel) {

        verificationRepository
                .findByApplicationAndVerificationLevel(
                        application,
                        nextLevel)
                .ifPresentOrElse(
                        verification -> {
                        },
                        () -> {
                            Verification nextVerification =
                                    new Verification();

                            nextVerification.setApplication(application);
                            nextVerification.setVerificationLevel(nextLevel);
                            nextVerification.setStatus("PENDING");

                            verificationRepository.save(nextVerification);
                        }
                );
    }

    private String getCurrentUsername() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(
                authentication.getName())) {

            return authentication.getName();
        }

        return "SYSTEM";
    }
}
