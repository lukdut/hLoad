package com.mark.gerasimov.highload.dao;

import com.mark.gerasimov.highload.model.Interest;

import java.util.List;
import java.util.UUID;

public interface InterestsDao {
    List<Interest> findAll();
    List<Interest> findAllById(List<UUID> ids);
}
