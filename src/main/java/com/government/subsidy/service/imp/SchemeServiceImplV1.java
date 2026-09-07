 package com.government.subsidy.service.imp;

import com.government.subsidy.entity.Scheme;
import com.government.subsidy.repository.SchemeRepository;
import com.government.subsidy.service.AuditLogService;
import com.government.subsidy.service.SchemeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("schemeServiceV1")
public class SchemeServiceImplV1 implements SchemeService {

    private final SchemeRepository schemeRepository;
    private final AuditLogService auditLogService;

    public SchemeServiceImplV1(
            SchemeRepository schemeRepository,
            AuditLogService auditLogService) {

        this.schemeRepository = schemeRepository;
        this.auditLogService = auditLogService;
    }

    @Override
    public Scheme createScheme(Scheme scheme) {

        Scheme savedScheme =
                schemeRepository.save(scheme);

        auditLogService.createAuditLog(
                "CREATE",
                "SCHEME",
                savedScheme.getId(),
                getCurrentUsername());

        return savedScheme;
    }

    @Override
    public Scheme getSchemeById(Long id) {

        return schemeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Scheme not found with id: " + id));
    }

    @Override
    public List<Scheme> getAllSchemes() {

        return schemeRepository.findAll();
    }

    @Override
    public Scheme updateScheme(
            Long id,
            Scheme scheme) {

        Scheme existingScheme =
                schemeRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Scheme not found with id: " + id));

        existingScheme.setName(scheme.getName());
        existingScheme.setDescription(scheme.getDescription());
        existingScheme.setEligibilityCriteria(
                scheme.getEligibilityCriteria());
        existingScheme.setGrantAmount(scheme.getGrantAmount());
        existingScheme.setRegion(scheme.getRegion());
        existingScheme.setAllocationBudget(
                scheme.getAllocationBudget());

        Scheme updatedScheme =
                schemeRepository.save(existingScheme);

        auditLogService.createAuditLog(
                "UPDATE",
                "SCHEME",
                updatedScheme.getId(),
                getCurrentUsername());

        return updatedScheme;
    }

    @Override
    public void deleteScheme(Long id) {

        schemeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Scheme not found with id: " + id));

        auditLogService.createAuditLog(
                "DELETE",
                "SCHEME",
                id,
                getCurrentUsername());

        schemeRepository.deleteById(id);
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
