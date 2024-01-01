package com.enigma.repository;

import com.enigma.entity.Chair;

import java.util.List;

public interface ChairRepo {
    List<Chair> getAll();
    void save(Chair chair);
    void update(Chair chair);
    void delete(Integer id);
    Chair getById(Integer id);
}
