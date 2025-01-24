package com.project.hanfu.service.impl;

import com.project.hanfu.mapper.SpeciesDao;
import com.project.hanfu.model.Species;
import com.project.hanfu.service.SpeciesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    @Resource
    private SpeciesDao speciesDao;

    @Override
    public int add(Species species) {
        return speciesDao.add(species);
    }

    @Override
    public int delete(int uid) {
        return speciesDao.delete(uid);
    }

    @Override
    public int update(Species species) {
        return speciesDao.update(species);
    }

    @Override
    public List<Species> find(String searchKey) {
        return speciesDao.find(searchKey);
    }

    @Override
    public List<Species> findAll() {
        return speciesDao.findAll();
    }
}
