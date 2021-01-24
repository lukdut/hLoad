package com.mark.gerasimov.highload.dao;

import com.mark.gerasimov.highload.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    Optional<User> findById(UUID id);

    User save(User user);
}
