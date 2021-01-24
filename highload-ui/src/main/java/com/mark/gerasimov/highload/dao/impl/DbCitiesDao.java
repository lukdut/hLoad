package com.mark.gerasimov.highload.dao.impl;

import com.mark.gerasimov.highload.dao.CitiesDao;
import com.mark.gerasimov.highload.model.City;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DbCitiesDao implements CitiesDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DbCitiesDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<City> findAll() {
        return jdbcTemplate.query("select * from cities", (resultSet, i) -> {
            final City city = new City();
            city.setId(UUID.fromString(resultSet.getString("id")));
            city.setName(resultSet.getString("name"));
            city.setPopulation(resultSet.getLong("population"));
            return city;
        });
    }
}
