package com.enigma.repository;

import com.enigma.entity.Movie;

import java.util.List;

public interface MovieRepo {
    List<Movie> getAll();
    void save(Movie movie);
    void update(Movie movie);
    void delete(Integer id);
    Movie getById(Integer id);
}
