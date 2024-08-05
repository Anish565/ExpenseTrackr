package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.*;
import com.example.demo.repositories.SettlementRepository;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.UserRepository;


@RestController
@RequestMapping("/settlements")
public class SettlementController {
    
    @Autowired
    private SettlementRepository settlementsRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    // Create Settlement
    @PostMapping
    public ResponseEntity<Settlements> createSettlement(@RequestBody Settlements settlement) {
        Settlements createdSettlement = settlementsRepository.save(settlement);
        return ResponseEntity.ok(createdSettlement);
    }

    // Get Settlement Details
    @GetMapping("/{settlementId}")
    public ResponseEntity<Settlements> getSettlementById(@PathVariable Long settlementId) {
        Optional<Settlements> settlement = settlementsRepository.findById(settlementId);
        return settlement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get Settlements by Group
    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<Settlements>> getSettlementsByGroup(@PathVariable Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()) {
            List<Settlements> settlements = settlementsRepository.findByGroup(group.get());
            return ResponseEntity.ok(settlements);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Settlements by User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Settlements>> getSettlementsByUser(@PathVariable Long userId) {
        List<Settlements> settlements = settlementsRepository.findByPayerIdOrReceiverId(userId, userId);
        return ResponseEntity.ok(settlements);
    }

}

