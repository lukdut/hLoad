package com.mark.gerasimov.highload.dao.impl;

import com.mark.gerasimov.highload.dao.UserInterestsDao;
import com.mark.gerasimov.highload.model.Interest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public List<Interest> findAllByUserId(UUID userId) {
        return jdbcTemplate.query("select interests.id as id, interests.name as name from users\n" +
                "inner join user_interests on user_interests.user_id = users.id\n" +
                "inner join interests on interests.id = user_interests.interest_id\n" +
                "where users.id=:uId", new MapSqlParameterSource("uId", userId.toString()), this::rowMapper);
    }

    private Interest rowMapper(ResultSet resultSet, int i) throws SQLException {
        final Interest interest = new Interest();
        interest.setName(resultSet.getString("name"));
        interest.setId(UUID.fromString(resultSet.getString("id")));
        return interest;
    }
}
