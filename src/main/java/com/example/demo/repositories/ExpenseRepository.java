package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.*;

import java.util.Date;
import java.util.List;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    public List<Expense> findByUser(User user);

    public List<Expense> findByUserAndCategory(Long userId, String category);

    public List<Expense> findByUserAndDate(User user, Date date);

    public List<Expense> findByUserAndDateAndCategory(User user, Date date, Category category);
}
