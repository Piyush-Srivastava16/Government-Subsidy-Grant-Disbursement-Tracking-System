package com.government.subsidy.service;

import java.util.Map;

public interface ApplicationAnalyticsService {

    Map<String, Long> getApplicationCountByStatus();
}
