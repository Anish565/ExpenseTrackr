package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.*;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.SettlementRepository;
import com.example.demo.repositories.SplitRepository;
import com.example.demo.repositories.UserRepository;

@RestController
@RequestMapping("/groups")
public class GroupController {
    
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SplitRepository splitRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    // Create Group
    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupRepository.save(group);
    }

    // Get Group Details
    @GetMapping("/{groupId}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        return group.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update Group Details
    @PutMapping("/{groupId}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long groupId, @RequestBody Group groupDetails) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()){
            Group group1 = group.get();
            group1.setName(groupDetails.getName());
            group1.setDate(groupDetails.getDate());
            group1.setUsers(groupDetails.getUsers());
            final Group updatedGroup = groupRepository.save(group1);
            return ResponseEntity.ok(updatedGroup);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Group
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()){
            groupRepository.delete(group.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add User to Group
    @PostMapping("/{groupId}/users/{userId}")
    public ResponseEntity<Group> addUserToGroup(@PathVariable long groupId, @PathVariable long userId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalGroup.isPresent() && optionalUser.isPresent()) {
            Group group = optionalGroup.get();
            User user = optionalUser.get();
            group.getUsers().add(user);
            final Group updatedGroup = groupRepository.save(group);
            return ResponseEntity.ok(updatedGroup);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // Remove User from Group
    @DeleteMapping("/{groupId}/users/{userId}")
    public ResponseEntity<Group> removeUserFromGroup(@PathVariable long groupId, @PathVariable long userId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalGroup.isPresent() && optionalUser.isPresent()) {
            Group group = optionalGroup.get();
            User user = optionalUser.get();
            group.getUsers().remove(user);
            final Group updatedGroup = groupRepository.save(group);
            return ResponseEntity.ok(updatedGroup);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Group Users
    @GetMapping("/{groupId}/users")
    public ResponseEntity<List<User>> getGroupUsers(@PathVariable Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        return group.map(g -> ResponseEntity.ok(g.getUsers())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get Group Splits
    @GetMapping("/{groupId}/splits")
    public ResponseEntity<List<Split>> getGroupSplits(@PathVariable Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        return group.map(g -> ResponseEntity.ok(g.getSplits())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get Group Settlements
    // @GetMapping("/{groupId}/settlements")
    // public ResponseEntity<List<Settlements>> getGroupSettlements(@PathVariable Long groupId) {
    //     Optional<Group> group = groupRepository.findById(groupId);
    //     return group.map(g -> ResponseEntity.ok(g.getSettlements())).orElseGet(() -> ResponseEntity.notFound().build());
    // }

}

