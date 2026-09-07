  package com.government.subsidy.service.imp;

import com.government.subsidy.repository.BeneficiaryRepository;
import com.government.subsidy.service.RegionalAnalyticsService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("regionalAnalyticsServiceV1")
public class RegionalAnalyticsServiceImplV1
        implements RegionalAnalyticsService {

    private final BeneficiaryRepository beneficiaryRepository;

    public RegionalAnalyticsServiceImplV1(
            BeneficiaryRepository beneficiaryRepository) {

        this.beneficiaryRepository = beneficiaryRepository;
    }

    @Override
    public Map<String, Long> getBeneficiaryCountByRegion() {

        List<Object[]> results =
                beneficiaryRepository.countBeneficiariesByRegion();

        Map<String, Long> regionalData =
                new LinkedHashMap<>();

        for (Object[] result : results) {

            String region = (String) result[0];

            Long count =
                    ((Number) result[1]).longValue();

            regionalData.put(region, count);
        }

        return regionalData;
    }
}

