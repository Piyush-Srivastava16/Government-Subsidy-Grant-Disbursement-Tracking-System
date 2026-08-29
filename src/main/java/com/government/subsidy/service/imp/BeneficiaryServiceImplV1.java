package com.government.subsidy.service.imp;

import com.government.subsidy.entity.Beneficiary;
import com.government.subsidy.repository.BeneficiaryRepository;
import com.government.subsidy.service.BeneficiaryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("beneficiaryServiceV1")
public class BeneficiaryServiceImplV1 implements BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;

    public BeneficiaryServiceImplV1(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    @Override
    public Beneficiary createBeneficiary(Beneficiary beneficiary) {
        return beneficiaryRepository.save(beneficiary);
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
    public Beneficiary updateBeneficiary(Long id, Beneficiary beneficiary) {

        Beneficiary existingBeneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Beneficiary not found with id: " + id));

        existingBeneficiary.setName(beneficiary.getName());
        existingBeneficiary.setCategory(beneficiary.getCategory());
        existingBeneficiary.setRegion(beneficiary.getRegion());
        existingBeneficiary.setIncome(beneficiary.getIncome());

        return beneficiaryRepository.save(existingBeneficiary);
    }

    @Override
    public void deleteBeneficiary(Long id) {

        beneficiaryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Beneficiary not found with id: " + id));

        beneficiaryRepository.deleteById(id);
    }
}