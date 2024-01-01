package com.enigma.repository;

import com.enigma.entity.Transaction;

import java.util.List;

public interface TransactionRepo {
    List<Transaction> getAll();
    void save(Transaction transaction);
    void update(Transaction transaction);
    void delete(Integer id);
    Transaction getById(Integer id);
}
