package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repositories.GroupRepository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.*;

public class GroupServices {
    
    @Autowired
    private GroupRepository groupRepository;

    public List<Group> findAllGroups() {
        List<Group> groups = (List<Group>) groupRepository.findAll();
        return groups;
    }

    public Optional<Group> findGroupById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group;
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroupById(Long id) {
        groupRepository.deleteById(id);
    }
}
