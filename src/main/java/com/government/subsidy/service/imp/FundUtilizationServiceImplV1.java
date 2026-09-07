 package com.government.subsidy.service.imp;

import com.government.subsidy.dto.FundUtilizationSummary;
import com.government.subsidy.entity.FundUtilization;
import com.government.subsidy.repository.DisbursementRepository;
import com.government.subsidy.repository.FundUtilizationRepository;
import com.government.subsidy.service.FundUtilizationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("fundUtilizationServiceV1")
public class FundUtilizationServiceImplV1
        implements FundUtilizationService {

    private final FundUtilizationRepository fundUtilizationRepository;

    private final DisbursementRepository disbursementRepository;

    public FundUtilizationServiceImplV1(
            FundUtilizationRepository fundUtilizationRepository,
            DisbursementRepository disbursementRepository) {

        this.fundUtilizationRepository =
                fundUtilizationRepository;

        this.disbursementRepository =
                disbursementRepository;
    }

    @Override
    public FundUtilization createFundUtilization(
            FundUtilization fundUtilization) {

        if (fundUtilization.getUtilizationDate() == null) {

            fundUtilization.setUtilizationDate(
                    LocalDate.now());
        }

        if (fundUtilization.getStatus() == null
                || fundUtilization.getStatus().isBlank()) {

            fundUtilization.setStatus("PENDING");
        }

        return fundUtilizationRepository.save(
                fundUtilization);
    }

    @Override
    public FundUtilization getFundUtilizationById(Long id) {

        return fundUtilizationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Fund Utilization not found with id: "
                                        + id));
    }

    @Override
    public List<FundUtilization> getAllFundUtilizations() {

        return fundUtilizationRepository.findAll();
    }

    @Override
    public FundUtilization updateFundUtilization(
            Long id,
            FundUtilization fundUtilization) {

        FundUtilization existingFundUtilization =
                fundUtilizationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Fund Utilization not found with id: "
                                                + id));

        if (fundUtilization.getUtilizedAmount() != null) {

            existingFundUtilization.setUtilizedAmount(
                    fundUtilization.getUtilizedAmount());
        }

        if (fundUtilization.getDescription() != null) {

            existingFundUtilization.setDescription(
                    fundUtilization.getDescription());
        }

        if (fundUtilization.getStatus() != null) {

            existingFundUtilization.setStatus(
                    fundUtilization.getStatus().toUpperCase());
        }

        return fundUtilizationRepository.save(
                existingFundUtilization);
    }

    @Override
    public void deleteFundUtilization(Long id) {

        if (!fundUtilizationRepository.existsById(id)) {

            throw new RuntimeException(
                    "Fund Utilization not found with id: " + id);
        }

        fundUtilizationRepository.deleteById(id);
    }

    // Fund Analytics Summary
    @Override
    public FundUtilizationSummary getFundUtilizationSummary() {

        Double totalReleasedAmount =
                disbursementRepository
                        .getTotalReleasedAmount();

        Double totalPendingAmount =
                disbursementRepository
                        .getTotalPendingAmount();

        long totalDisbursements =
                disbursementRepository.count();

        return new FundUtilizationSummary(
                totalReleasedAmount,
                totalPendingAmount,
                totalDisbursements
        );
    }
}

