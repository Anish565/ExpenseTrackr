package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.*;

import java.util.Date;
import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Long> {
    
    public List<Group> findByUsers(User user);

    public List<Group> findByAdmin(User user);

    public List<Group> findByDate(Date date);

    public List<Group> findByName(String name);

    public List<Group> findByDateAndName(Date date, String name);
}
