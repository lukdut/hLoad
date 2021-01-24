package com.mark.gerasimov.highload.dao;

import java.util.List;
import java.util.UUID;

public interface UserInterestsDao {
    void saveUserInterests(UUID userId, List<UUID> interestIds);
}
