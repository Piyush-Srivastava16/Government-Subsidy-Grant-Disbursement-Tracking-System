package com.government.subsidy.service.imp;

import com.government.subsidy.entity.Subsidy;
import com.government.subsidy.exception.SubsidyNotFoundException;
import com.government.subsidy.repository.SubsidyRepository;
import com.government.subsidy.service.SubsidyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("subsidyServiceV2")
public class SubsidyServiceImplV2 implements SubsidyService {

    private final SubsidyRepository subsidyRepository;

    public SubsidyServiceImplV2(SubsidyRepository subsidyRepository) {
        this.subsidyRepository = subsidyRepository;
    }

    @Override
    public Subsidy createSubsidy(Subsidy subsidy) {

        if (subsidy.getStatus() == null || subsidy.getStatus().isBlank()) {
            subsidy.setStatus("PENDING");
        }

        return subsidyRepository.save(subsidy);
    }

    @Override
    public Subsidy getSubsidyById(Long id) {

        return subsidyRepository.findById(id)
                .orElseThrow(() ->
                        new SubsidyNotFoundException(
                                "Subsidy not found with id: " + id));
    }

    @Override
    public List<Subsidy> getAllSubsidies() {

        return subsidyRepository.findAll();
    }

    @Override
    public Subsidy updateSubsidy(Long id, Subsidy subsidy) {

        Subsidy existingSubsidy = subsidyRepository.findById(id)
                .orElseThrow(() ->
                        new SubsidyNotFoundException(
                                "Subsidy not found with id: " + id));

        if (subsidy.getName() != null) {
            existingSubsidy.setName(subsidy.getName());
        }

        if (subsidy.getDescription() != null) {
            existingSubsidy.setDescription(subsidy.getDescription());
        }

        if (subsidy.getAmount() != null) {
            existingSubsidy.setAmount(subsidy.getAmount());
        }

        if (subsidy.getBeneficiaryName() != null) {
            existingSubsidy.setBeneficiaryName(
                    subsidy.getBeneficiaryName());
        }

        if (subsidy.getStatus() != null) {
            existingSubsidy.setStatus(subsidy.getStatus());
        }

        return subsidyRepository.save(existingSubsidy);
    }

    @Override
    public void deleteSubsidy(Long id) {

        if (!subsidyRepository.existsById(id)) {
            throw new SubsidyNotFoundException(
                    "Subsidy not found with id: " + id);
        }

        subsidyRepository.deleteById(id);
    }
}