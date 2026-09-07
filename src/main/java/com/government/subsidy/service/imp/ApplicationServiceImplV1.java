package com.government.subsidy.service.imp;

import com.government.subsidy.entity.Application;
import com.government.subsidy.repository.ApplicationRepository;
import com.government.subsidy.service.ApplicationService;
import com.government.subsidy.service.AuditLogService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("applicationServiceV1")
public class ApplicationServiceImplV1 implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final AuditLogService auditLogService;

    public ApplicationServiceImplV1(
            ApplicationRepository applicationRepository,
            AuditLogService auditLogService) {

        this.applicationRepository = applicationRepository;
        this.auditLogService = auditLogService;
    }

    @Override
    public Application createApplication(Application application) {

        application.setApplicationDate(LocalDate.now());

        if (application.getStatus() == null
                || application.getStatus().isBlank()) {

            application.setStatus("SUBMITTED");
        }

        calculateEligibilityScore(application);

        Application savedApplication =
                applicationRepository.save(application);

        auditLogService.createAuditLog(
                "CREATE",
                "APPLICATION",
                savedApplication.getId(),
                getCurrentUsername());

        return savedApplication;
    }

    private void calculateEligibilityScore(Application application) {

        if (application.getBeneficiary() != null
                && application.getBeneficiary().getIncome() != null) {

            Double income = application.getBeneficiary().getIncome();

            if (income <= 300000) {
                application.setEligibilityScore(90.0);
            } else if (income <= 500000) {
                application.setEligibilityScore(70.0);
            } else {
                application.setEligibilityScore(40.0);
            }
        }
    }

    @Override
    public Application getApplicationById(Long id) {

        return applicationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found with id: " + id));
    }

    @Override
    public List<Application> getAllApplications() {

        return applicationRepository.findAll();
    }

    @Override
    public Application updateApplication(
            Long id,
            Application application) {

        Application existingApplication =
                applicationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found with id: " + id));

        existingApplication.setStatus(application.getStatus());

        Application updatedApplication =
                applicationRepository.save(existingApplication);

        auditLogService.createAuditLog(
                "UPDATE",
                "APPLICATION",
                updatedApplication.getId(),
                getCurrentUsername());

        return updatedApplication;
    }

    @Override
    public void deleteApplication(Long id) {

        applicationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found with id: " + id));

        auditLogService.createAuditLog(
                "DELETE",
                "APPLICATION",
                id,
                getCurrentUsername());

        applicationRepository.deleteById(id);
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

