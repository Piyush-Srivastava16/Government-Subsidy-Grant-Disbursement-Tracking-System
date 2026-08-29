package com.government.subsidy.service.imp;

import com.government.subsidy.entity.Scheme;
import com.government.subsidy.repository.SchemeRepository;
import com.government.subsidy.service.SchemeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("schemeServiceV1")
public class SchemeServiceImplV1 implements SchemeService {

    private final SchemeRepository schemeRepository;

    public SchemeServiceImplV1(SchemeRepository schemeRepository) {
        this.schemeRepository = schemeRepository;
    }

    @Override
    public Scheme createScheme(Scheme scheme) {
        return schemeRepository.save(scheme);
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
    public Scheme updateScheme(Long id, Scheme scheme) {

        Scheme existingScheme = schemeRepository.findById(id)
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

        return schemeRepository.save(existingScheme);
    }

    @Override
    public void deleteScheme(Long id) {

        schemeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Scheme not found with id: " + id));

        schemeRepository.deleteById(id);
    }
}