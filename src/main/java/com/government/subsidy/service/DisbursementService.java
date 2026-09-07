package com.government.subsidy.service;

import com.government.subsidy.entity.Disbursement;

import java.util.List;

public interface DisbursementService {

    Disbursement createDisbursement(Disbursement disbursement);

    Disbursement getDisbursementById(Long id);

    List<Disbursement> getAllDisbursements();

    Disbursement updateDisbursement(
            Long id,
            Disbursement disbursement);

    void deleteDisbursement(Long id);
}