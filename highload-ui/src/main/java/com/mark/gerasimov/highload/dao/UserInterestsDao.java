package com.mark.gerasimov.highload.dao;

import com.mark.gerasimov.highload.model.Interest;

import java.util.List;
import java.util.UUID;

public interface UserInterestsDao {
    void saveUserInterests(UUID userId, List<UUID> interestIds);

    List<Interest> findAllByUserId(UUID userId);
}
