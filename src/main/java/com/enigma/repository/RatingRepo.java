package com.enigma.repository;

import com.enigma.entity.Rating;

import java.util.List;

public interface RatingRepo {
    List<Rating> getAll();
    void save(Rating rating);
    void update(Rating rating);
    void delete(Integer id);
    Rating getById(Integer id);
}
