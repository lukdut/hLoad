package com.mark.gerasimov.highload.dao.impl;

import com.mark.gerasimov.highload.dao.InterestsDao;
import com.mark.gerasimov.highload.model.Interest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DbInterestsDao implements InterestsDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DbInterestsDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Interest> findAll() {
        return jdbcTemplate.query("select * from interests", (resultSet, i) -> {
            Interest interest = new Interest();
            interest.setId(UUID.fromString(resultSet.getString("id")));
            interest.setName(resultSet.getString("name"));
            return interest;
        });
    }
}
