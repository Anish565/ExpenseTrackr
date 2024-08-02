package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.*;


public interface SplitRepository extends CrudRepository<Split, Long> {

    public List<Split> findByGroup(Group group);

    public Optional<Split> findById(Long id);

}
