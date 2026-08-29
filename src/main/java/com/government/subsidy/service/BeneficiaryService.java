package com.government.subsidy.service;

import com.government.subsidy.entity.Beneficiary;

import java.util.List;

public interface BeneficiaryService {

    Beneficiary createBeneficiary(Beneficiary beneficiary);

    Beneficiary getBeneficiaryById(Long id);

    List<Beneficiary> getAllBeneficiaries();

    Beneficiary updateBeneficiary(Long id, Beneficiary beneficiary);

    void deleteBeneficiary(Long id);
}