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

import com.example.demo.DTOs.SplitDTO;
import com.example.demo.entities.*;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.SplitRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.SplitServices;

@RestController
@RequestMapping("/splits")
public class SplitController {
    
    @Autowired
    private SplitRepository splitRepository;

    @Autowired
    private SplitServices splitServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Create a split
    @PostMapping("{groupId}")
    public ResponseEntity<SplitDTO> createSplit(@PathVariable Long groupId, @RequestBody SplitDTO splitDetails){
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        User payee = userRepository.findById(splitDetails.payeeId()).orElseThrow(() -> new RuntimeException("Payee not found"));
        User payer = userRepository.findById(splitDetails.payerId()).orElseThrow(() -> new RuntimeException("Payer not found"));
        Category category = categoryRepository.findByName(splitDetails.categoryName()).orElseThrow(() -> new RuntimeException("Category not found"));
        Split split = new Split();
        split.setGroup(group);
        split.setPayee(payee);
        split.setPayer(payer);
        split.setCategory(category);
        split.setAmount(splitDetails.amount());
        split.setSettled(false);
        splitServices.saveSplit(split);
        SplitDTO splitDTO = new SplitDTO(split.getId(), split.getAmount(), split.getGroup().getId(), split.getPayer().getId(), split.getPayee().getId(), split.getCategory().getName(), split.isSettled());
        return ResponseEntity.ok(splitDTO);
    }

    // Get Split Details
    @GetMapping("/{splitId}")
    public ResponseEntity<Optional<SplitDTO>> getSplitById(@PathVariable Long splitId){
        Optional<SplitDTO> split = splitServices.findSplitById(splitId);
        return ResponseEntity.ok(split);
    }
 
    // Update Split Details
    @PutMapping("/{splitId}")
    public ResponseEntity<SplitDTO> updateSplit(@PathVariable Long splitId, @RequestBody SplitDTO splitDetails){
        Split updatedSplit = splitServices.updateSplit(splitId, splitDetails);
        SplitDTO splitDTO = new SplitDTO(updatedSplit.getId(), updatedSplit.getAmount(), updatedSplit.getGroup().getId(), updatedSplit.getPayer().getId(), updatedSplit.getPayee().getId(), updatedSplit.getCategory().getName(), updatedSplit.isSettled());
        return ResponseEntity.ok(splitDTO);
    }

    // Delete Split
    @DeleteMapping("/{splitId}")
    public Object deleteSplit(@PathVariable Long splitId) {
        return splitServices.deleteSplit(splitId);
    }

     // Get Splits by User
     @GetMapping("/user/{userId}")
     public ResponseEntity<List<SplitDTO>> getSplitsByUser(@PathVariable Long userId) {
         List<SplitDTO> splits = splitServices.getSplitsByUser(userId);
         return ResponseEntity.ok(splits);
     }
 
     // Get Splits by Group
     @GetMapping("/group/{groupId}")
     public ResponseEntity<List<SplitDTO>> getSplitsByGroup(@PathVariable Long groupId) {
         List<SplitDTO> splits = splitServices.getSplitsByGroup(groupId);
         return ResponseEntity.ok(splits);
     }
}
