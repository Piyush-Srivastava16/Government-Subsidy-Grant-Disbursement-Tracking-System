package com.government.subsidy.service;

import com.government.subsidy.entity.Scheme;

import java.util.List;

public interface SchemeService {

    Scheme createScheme(Scheme scheme);

    Scheme getSchemeById(Long id);

    List<Scheme> getAllSchemes();

    Scheme updateScheme(Long id, Scheme scheme);

    void deleteScheme(Long id);
}