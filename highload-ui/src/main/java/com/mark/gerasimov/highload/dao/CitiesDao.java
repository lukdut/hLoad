package com.mark.gerasimov.highload.dao;

import com.mark.gerasimov.highload.model.City;

import java.util.List;
import java.util.UUID;

public interface CitiesDao {
    List<City> findAll();
    City findById(UUID id);
}
