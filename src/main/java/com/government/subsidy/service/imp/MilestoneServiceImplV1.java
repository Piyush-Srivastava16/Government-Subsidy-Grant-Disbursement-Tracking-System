 package com.government.subsidy.service.imp;

import com.government.subsidy.entity.Disbursement;
import com.government.subsidy.entity.Milestone;
import com.government.subsidy.repository.DisbursementRepository;
import com.government.subsidy.repository.MilestoneRepository;
import com.government.subsidy.service.AuditLogService;
import com.government.subsidy.service.MilestoneService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service("milestoneServiceV1")
public class MilestoneServiceImplV1 implements MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final DisbursementRepository disbursementRepository;
    private final AuditLogService auditLogService;

    public MilestoneServiceImplV1(
            MilestoneRepository milestoneRepository,
            DisbursementRepository disbursementRepository,
            AuditLogService auditLogService) {

        this.milestoneRepository = milestoneRepository;
        this.disbursementRepository = disbursementRepository;
        this.auditLogService = auditLogService;
    }

    @Override
    public Milestone createMilestone(Milestone milestone) {

        if (milestone.getStatus() == null
                || milestone.getStatus().isBlank()) {

            milestone.setStatus("PENDING");
        }

        Milestone savedMilestone =
                milestoneRepository.save(milestone);

        auditLogService.createAuditLog(
                "CREATE",
                "MILESTONE",
                savedMilestone.getId(),
                getCurrentUsername());

        return savedMilestone;
    }

    @Override
    public Milestone getMilestoneById(Long id) {

        return milestoneRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Milestone not found with id: " + id));
    }

    @Override
    public List<Milestone> getAllMilestones() {

        return milestoneRepository.findAll();
    }

    @Override
    public Milestone updateMilestone(
            Long id,
            Milestone milestone) {

        Milestone existingMilestone =
                milestoneRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Milestone not found with id: " + id));

        if (milestone.getMilestoneName() != null) {
            existingMilestone.setMilestoneName(
                    milestone.getMilestoneName());
        }

        if (milestone.getDueDate() != null) {
            existingMilestone.setDueDate(
                    milestone.getDueDate());
        }

        if (milestone.getStatus() != null) {
            existingMilestone.setStatus(
                    milestone.getStatus().toUpperCase());
        }

        if (milestone.getRemarks() != null) {
            existingMilestone.setRemarks(
                    milestone.getRemarks());
        }

        Milestone updatedMilestone =
                milestoneRepository.save(existingMilestone);

        auditLogService.createAuditLog(
                "UPDATE",
                "MILESTONE",
                updatedMilestone.getId(),
                getCurrentUsername());

        // Automatic Disbursement Release
        if ("COMPLETED".equalsIgnoreCase(
                updatedMilestone.getStatus())) {

            Optional<Disbursement> disbursementOptional =
                    disbursementRepository.findByMilestone(
                            updatedMilestone);

            if (disbursementOptional.isPresent()) {

                Disbursement disbursement =
                        disbursementOptional.get();

                disbursement.setStatus("RELEASED");
                disbursement.setDisbursementDate(
                        LocalDate.now());

                disbursementRepository.save(disbursement);

                // Audit automatic disbursement release
                auditLogService.createAuditLog(
                        "AUTO_RELEASE",
                        "DISBURSEMENT",
                        disbursement.getId(),
                        getCurrentUsername());
            }
        }

        return updatedMilestone;
    }

    @Override
    public void deleteMilestone(Long id) {

        if (!milestoneRepository.existsById(id)) {

            throw new RuntimeException(
                    "Milestone not found with id: " + id);
        }

        auditLogService.createAuditLog(
                "DELETE",
                "MILESTONE",
                id,
                getCurrentUsername());

        milestoneRepository.deleteById(id);
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

