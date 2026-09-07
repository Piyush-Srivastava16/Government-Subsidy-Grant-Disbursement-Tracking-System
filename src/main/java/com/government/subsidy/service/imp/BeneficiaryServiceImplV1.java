package com.government.subsidy.service.imp;

import com.government.subsidy.entity.Beneficiary;
import com.government.subsidy.repository.BeneficiaryRepository;
import com.government.subsidy.service.AuditLogService;
import com.government.subsidy.service.BeneficiaryService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@Service("beneficiaryServiceV1")
public class BeneficiaryServiceImplV1 implements BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;
    private final AuditLogService auditLogService;

    public BeneficiaryServiceImplV1(
            BeneficiaryRepository beneficiaryRepository,
            AuditLogService auditLogService) {

        this.beneficiaryRepository = beneficiaryRepository;
        this.auditLogService = auditLogService;
    }

    @Override
    public Beneficiary createBeneficiary(Beneficiary beneficiary) {

        Beneficiary savedBeneficiary =
                beneficiaryRepository.save(beneficiary);

        auditLogService.createAuditLog(
                "CREATE",
                "BENEFICIARY",
                savedBeneficiary.getId(),
                getCurrentUsername());

        return savedBeneficiary;
    }

    @Override
    public Beneficiary getBeneficiaryById(Long id) {

        return beneficiaryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Beneficiary not found with id: " + id));
    }

    @Override
    public List<Beneficiary> getAllBeneficiaries() {

        return beneficiaryRepository.findAll();
    }

    @Override
    public Beneficiary updateBeneficiary(
            Long id,
            Beneficiary beneficiary) {

        Beneficiary existingBeneficiary =
                beneficiaryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Beneficiary not found with id: " + id));

        existingBeneficiary.setName(beneficiary.getName());
        existingBeneficiary.setCategory(beneficiary.getCategory());
        existingBeneficiary.setRegion(beneficiary.getRegion());
        existingBeneficiary.setIncome(beneficiary.getIncome());

        Beneficiary updatedBeneficiary =
                beneficiaryRepository.save(existingBeneficiary);

        auditLogService.createAuditLog(
                "UPDATE",
                "BENEFICIARY",
                updatedBeneficiary.getId(),
                getCurrentUsername());

        return updatedBeneficiary;
    }

    @Override
    public void deleteBeneficiary(Long id) {

        beneficiaryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Beneficiary not found with id: " + id));

        auditLogService.createAuditLog(
                "DELETE",
                "BENEFICIARY",
                id,
                getCurrentUsername());

        beneficiaryRepository.deleteById(id);
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

