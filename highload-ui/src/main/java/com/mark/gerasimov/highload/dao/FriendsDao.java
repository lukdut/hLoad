package com.mark.gerasimov.highload.dao;

import com.mark.gerasimov.highload.model.User;

import java.util.List;
import java.util.UUID;

public interface FriendsDao {
    List<User> findUserFriends(UUID userId);
}
