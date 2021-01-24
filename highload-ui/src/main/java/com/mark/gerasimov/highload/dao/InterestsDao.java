package com.mark.gerasimov.highload.dao;

import com.mark.gerasimov.highload.model.Interest;

import java.util.List;

public interface InterestsDao {
    List<Interest> findAll();
}
