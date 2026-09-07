package com.government.subsidy.service;

import com.government.subsidy.entity.Milestone;

import java.util.List;

public interface MilestoneService {

    Milestone createMilestone(Milestone milestone);

    Milestone getMilestoneById(Long id);

    List<Milestone> getAllMilestones();

    Milestone updateMilestone(Long id, Milestone milestone);

    void deleteMilestone(Long id);
}