package com.mark.gerasimov.highload.dao.impl;

import com.mark.gerasimov.highload.dao.UserDao;
import com.mark.gerasimov.highload.model.Gender;
import com.mark.gerasimov.highload.model.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DbUserDao implements UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DbUserDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(UUID id) {
        final List<User> allFoundUsers = jdbcTemplate.query("Select * from users where id =:id",
                new MapSqlParameterSource("id", id.toString()),
                (resultSet, i) -> {
                    User user = new User();

                    user.setName(resultSet.getString("name"));

                    user.setLastName(resultSet.getString("last_name"));

                    final String genderString = resultSet.getString("gender");
                    user.setGender(genderString == null ? null : Gender.valueOf(genderString));

                    final Date birthday = resultSet.getDate("birthday");
                    if (birthday != null) {
                        user.setBirthDay(birthday.toLocalDate());
                    }
                    user.setId(UUID.fromString(resultSet.getString("id")));

                    final String cityId = resultSet.getString("city_id");
                    if (cityId != null) {
                        user.setCityId(UUID.fromString(cityId));
                    }

                    return user;
                });

        return allFoundUsers.isEmpty() ? Optional.empty() : Optional.of(allFoundUsers.get(0));
    }

    @Override
    public User save(User user) {
        boolean isNew = user.getId() == null;

        if (isNew) {
            user.setId(UUID.randomUUID());
        }

        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", user.getId().toString());
        if (user.getBirthDay() != null) {
            parameters.addValue("birthday", Date.from(user.getBirthDay().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } else {
            parameters.addValue("birthday", null);
        }
        parameters.addValue("name", user.getName());
        parameters.addValue("lastName", user.getLastName());
        parameters.addValue("city_id", user.getCityId() == null ? null : user.getCityId().toString());
        parameters.addValue("gender", user.getGender() == null ? null : user.getGender().toString());

        if (isNew) {
            jdbcTemplate.update("INSERT INTO users (id, name, last_name, city_id, birthday, gender) VALUES (:id, :name, :lastName, :city_id, :birthday, :gender)", parameters);
        } else {
            jdbcTemplate.update("UPDATE users set name=:name, lastName=:lastName, city_id=:city_id, birthday=:birthday, gender=:gender where id=:id", parameters);
        }

        return user;
    }
}
