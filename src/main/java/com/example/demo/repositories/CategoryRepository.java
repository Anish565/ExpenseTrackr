package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.*;

import java.util.List;



public interface CategoryRepository extends CrudRepository<Category, Long> {

    public List<Category> findByName(String name);


}
