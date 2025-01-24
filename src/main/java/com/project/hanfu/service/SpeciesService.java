package com.project.hanfu.service;

import com.project.hanfu.model.Species;

import java.util.List;

public interface SpeciesService {

    int add(Species species);
    int delete(int id);
    int update(Species species);
    List<Species> find(String searchKey);
    List<Species> findAll();
}
