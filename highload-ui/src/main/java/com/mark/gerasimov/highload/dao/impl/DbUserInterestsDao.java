package com.mark.gerasimov.highload.dao.impl;

import com.mark.gerasimov.highload.dao.UserInterestsDao;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DbUserInterestsDao implements UserInterestsDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DbUserInterestsDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveUserInterests(UUID userId, List<UUID> interestIds) {
        final MapSqlParameterSource[] parameterSources = interestIds.stream().map(iId -> {
            final MapSqlParameterSource parameterSource = new MapSqlParameterSource("uId", userId.toString());
            parameterSource.addValue("iId", iId.toString());
            return parameterSource;
        }).toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate("insert into user_interests (user_id, interest_id) values (:uId, :iId)", parameterSources);
    }
}
