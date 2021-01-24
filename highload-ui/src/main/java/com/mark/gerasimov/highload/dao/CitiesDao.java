package com.mark.gerasimov.highload.dao;

import com.mark.gerasimov.highload.model.City;

import java.util.List;

public interface CitiesDao {
    List<City> findAll();
}
