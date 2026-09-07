package com.government.subsidy.service.imp;

import com.government.subsidy.repository.ApplicationRepository;
import com.government.subsidy.service.ApplicationAnalyticsService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("applicationAnalyticsServiceV1")
public class ApplicationAnalyticsServiceImplV1
        implements ApplicationAnalyticsService {

    private final ApplicationRepository applicationRepository;

    public ApplicationAnalyticsServiceImplV1(
            ApplicationRepository applicationRepository) {

        this.applicationRepository = applicationRepository;
    }

    @Override
    public Map<String, Long> getApplicationCountByStatus() {

        List<Object[]> results =
                applicationRepository.countApplicationsByStatus();

        Map<String, Long> statusData =
                new LinkedHashMap<>();

        for (Object[] result : results) {

            String status = (String) result[0];

            Long count =
                    ((Number) result[1]).longValue();

            statusData.put(status, count);
        }

        return statusData;
    }
}

