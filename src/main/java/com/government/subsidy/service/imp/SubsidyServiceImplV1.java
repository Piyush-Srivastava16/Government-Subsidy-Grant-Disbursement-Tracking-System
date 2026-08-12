package com.government.subsidy.service.imp;

import com.government.subsidy.entity.Subsidy;
import com.government.subsidy.exception.SubsidyNotFoundException;
import com.government.subsidy.repository.SubsidyRepository;
import com.government.subsidy.service.SubsidyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("subsidyServiceV1")    // "@Service marks a class as a service component containing business logic." Spring will automatically create and manage an instance of UserService as a Spring Bean.
public class SubsidyServiceImplV1 implements SubsidyService {

   private final SubsidyRepository subsidyRepository;  //  final ---> If once subsidyRepository is assigned, it cannot be assigned to another repository later.

    public SubsidyServiceImplV1(SubsidyRepository subsidyRepository) {
        this.subsidyRepository = subsidyRepository;
    }

    @Override
    public Subsidy createSubsidy(Subsidy subsidy) {
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

        existingSubsidy.setName(subsidy.getName());
        existingSubsidy.setDescription(subsidy.getDescription());
        existingSubsidy.setAmount(subsidy.getAmount());
        existingSubsidy.setBeneficiaryName(subsidy.getBeneficiaryName());
        existingSubsidy.setStatus(subsidy.getStatus());

        return subsidyRepository.save(existingSubsidy);
    }

    @Override
    public void deleteSubsidy(Long id) {

        subsidyRepository.findById(id)
                .orElseThrow(() ->
                        new SubsidyNotFoundException(
                                "Subsidy not found with id: " + id));

        subsidyRepository.deleteById(id);
    }
}
