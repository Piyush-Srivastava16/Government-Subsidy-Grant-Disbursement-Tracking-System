package com.government.subsidy.service;

import com.government.subsidy.entity.Application;

import java.util.List;

public interface ApplicationService {

    Application createApplication(Application application);

    Application getApplicationById(Long id);

    List<Application> getAllApplications();

    Application updateApplication(Long id, Application application);

    void deleteApplication(Long id);
}