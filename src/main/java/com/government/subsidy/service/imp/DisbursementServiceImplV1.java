
package com.government.subsidy.service.imp;

import com.government.subsidy.entity.Disbursement;
import com.government.subsidy.repository.DisbursementRepository;
import com.government.subsidy.service.AuditLogService;
import com.government.subsidy.service.DisbursementService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("disbursementServiceV1")
public class DisbursementServiceImplV1 implements DisbursementService {

    private final DisbursementRepository disbursementRepository;
    private final AuditLogService auditLogService;

    public DisbursementServiceImplV1(
            DisbursementRepository disbursementRepository,
            AuditLogService auditLogService) {

        this.disbursementRepository = disbursementRepository;
        this.auditLogService = auditLogService;
    }

    @Override
    public Disbursement createDisbursement(
            Disbursement disbursement) {

        if (disbursement.getStatus() == null
                || disbursement.getStatus().isBlank()) {

            disbursement.setStatus("PENDING");
        }

        Disbursement savedDisbursement =
                disbursementRepository.save(disbursement);

        auditLogService.createAuditLog(
                "CREATE",
                "DISBURSEMENT",
                savedDisbursement.getId(),
                getCurrentUsername());

        return savedDisbursement;
    }

    @Override
    public Disbursement getDisbursementById(Long id) {

        return disbursementRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Disbursement not found with id: " + id));
    }

    @Override
    public List<Disbursement> getAllDisbursements() {

        return disbursementRepository.findAll();
    }

    @Override
    public Disbursement updateDisbursement(
            Long id,
            Disbursement disbursement) {

        Disbursement existingDisbursement =
                disbursementRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Disbursement not found with id: " + id));

        if (disbursement.getAmount() != null) {
            existingDisbursement.setAmount(
                    disbursement.getAmount());
        }

        if (disbursement.getStatus() != null) {

            existingDisbursement.setStatus(
                    disbursement.getStatus().toUpperCase());

            if ("RELEASED".equalsIgnoreCase(
                    disbursement.getStatus())) {

                existingDisbursement.setDisbursementDate(
                        LocalDate.now());
            }
        }

        Disbursement updatedDisbursement =
                disbursementRepository.save(existingDisbursement);

        auditLogService.createAuditLog(
                "UPDATE",
                "DISBURSEMENT",
                updatedDisbursement.getId(),
                getCurrentUsername());

        return updatedDisbursement;
    }

    @Override
    public void deleteDisbursement(Long id) {

        if (!disbursementRepository.existsById(id)) {

            throw new RuntimeException(
                    "Disbursement not found with id: " + id);
        }

        auditLogService.createAuditLog(
                "DELETE",
                "DISBURSEMENT",
                id,
                getCurrentUsername());

        disbursementRepository.deleteById(id);
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

