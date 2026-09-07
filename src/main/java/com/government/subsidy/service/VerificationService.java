package com.government.subsidy.service;

import com.government.subsidy.entity.Verification;

import java.util.List;

public interface VerificationService {

    Verification createVerification(Verification verification);

    Verification getVerificationById(Long id);

    List<Verification> getAllVerifications();

    Verification updateVerification(Long id, Verification verification);

    void deleteVerification(Long id);
}