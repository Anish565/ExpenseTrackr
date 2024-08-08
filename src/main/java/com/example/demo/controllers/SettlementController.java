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

import com.example.demo.DTOs.SettlementDTO;
import com.example.demo.entities.*;
import com.example.demo.repositories.SettlementRepository;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.SettlementServices;


@RestController
@RequestMapping("/settlements")
public class SettlementController {
    
    @Autowired
    private SettlementRepository settlementsRepository;

    @Autowired
    private SettlementServices settlementsServices;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    // Create Settlement
    @PostMapping
    public ResponseEntity<SettlementDTO> createSettlement(@RequestBody SettlementDTO settlement) {
        Optional<Group> group = groupRepository.findById(settlement.groupId());
        if (group.isPresent()) {
            Optional<User> payer = userRepository.findById(settlement.payerId());
            if (payer.isPresent()) {
                Optional<User> receiver = userRepository.findById(settlement.receiverId());
                if (receiver.isPresent()) {
                    Settlements newSettlement = new Settlements();
                    newSettlement.setGroup(group.get());
                    newSettlement.setPayer(payer.get());
                    newSettlement.setReceiver(receiver.get());
                    newSettlement.setAmount(settlement.amount());
                    newSettlement.setSettledDate(settlement.settledDate());
                    Settlements savedSettlement = settlementsServices.savSettlements(newSettlement);
                    SettlementDTO settlementDTO = new SettlementDTO(
                        savedSettlement.getId(),
                        savedSettlement.getAmount(),
                        savedSettlement.getSettledDate(),
                        savedSettlement.getGroup().getId(),
                        savedSettlement.getPayer().getId(),
                        savedSettlement.getReceiver().getId()
                    );
                    return ResponseEntity.ok(settlementDTO);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Settlement Details
    @GetMapping("/{settlementId}")
    public ResponseEntity<SettlementDTO> getSettlementById(@PathVariable Long settlementId) {
        Optional<SettlementDTO> settlement = settlementsServices.findSettlementsById(settlementId);
        return settlement.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Get Settlements by Group
    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<SettlementDTO>> getSettlementsByGroup(@PathVariable Long groupId) {
        List<SettlementDTO> settlements = settlementsServices.findSettlementsByGroupId(groupId);
        return ResponseEntity.ok(settlements);
        
    }

    // Get Settlements by User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SettlementDTO>> getSettlementsByUser(@PathVariable Long userId) {
        List<SettlementDTO> settlements = settlementsServices.findSettlementsByUsersId(userId);
        return ResponseEntity.ok(settlements);
    }

}

