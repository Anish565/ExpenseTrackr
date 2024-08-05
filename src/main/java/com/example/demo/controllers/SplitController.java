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
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.SplitRepository;
import com.example.demo.repositories.UserRepository;

@RestController
@RequestMapping("/splits")
public class SplitController {
    
    @Autowired
    private SplitRepository splitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Create a split
    @PostMapping("{groupId}")
    public ResponseEntity<Split> createSplit(@PathVariable Long groupId, @RequestBody Split splitDetails){
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()){
            Group group1 = group.get();
            splitDetails.setGroup(group1);
            final Split createdSplit = splitRepository.save(splitDetails);
            return ResponseEntity.ok(createdSplit); 
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Split Details
    @GetMapping("/{splitId}")
    public ResponseEntity<Split> getSplitById(@PathVariable Long splitId){
        Optional<Split> split = splitRepository.findById(splitId);
        return split.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
 
    // Update Split Details
    @PutMapping("/{splitId}")
    public ResponseEntity<Split> updateSplit(@PathVariable Long splitId, @RequestBody Split splitDetails){
        Optional<Split> optionalSplit = splitRepository.findById(splitId);
        if (optionalSplit.isPresent()){
            Split split = optionalSplit.get();
            Optional<User> payee = userRepository.findById(splitDetails.getPayee().getId());
            if (!payee.isPresent()){
                return ResponseEntity.badRequest().build();
            }
            Optional<User> payer = userRepository.findById(splitDetails.getPayer().getId());
            if (!payer.isPresent()){
                return ResponseEntity.badRequest().build();
            }
            Optional<Category> category = categoryRepository.findById(splitDetails.getCategory().getId());
            if (!category.isPresent()){
                return ResponseEntity.badRequest().build();
            }
            split.setGroup(splitDetails.getGroup());
            split.setPayee(splitDetails.getPayee());
            split.setPayer(splitDetails.getPayer());
            split.setCategory(splitDetails.getCategory());
            split.setAmount(splitDetails.getAmount());
            split.setSettled(splitDetails.isSettled());
            final Split updatedSplit = splitRepository.save(split);
            return ResponseEntity.ok(updatedSplit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Split
    @DeleteMapping("/{splitId}")
    public ResponseEntity<Void> deleteSplit(@PathVariable Long splitId) {
        Optional<Split> optionalSplit = splitRepository.findById(splitId);
        if (optionalSplit.isPresent()) {
            splitRepository.delete(optionalSplit.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

     // Get Splits by User
     @GetMapping("/user/{userId}")
     public ResponseEntity<List<Split>> getSplitsByUser(@PathVariable Long userId) {
         Optional<User> user = userRepository.findById(userId);
         if (user.isPresent()) {
             List<Split> splits = splitRepository.findByPayerOrPayee(user.get(), user.get());
             return ResponseEntity.ok(splits);
         } else {
             return ResponseEntity.notFound().build();
         }
     }
 
     // Get Splits by Group
     @GetMapping("/group/{groupId}")
     public ResponseEntity<List<Split>> getSplitsByGroup(@PathVariable Long groupId) {
         Optional<Group> group = groupRepository.findById(groupId);
         if (group.isPresent()) {
             List<Split> splits = splitRepository.findByGroup(group.get());
             return ResponseEntity.ok(splits);
         } else {
             return ResponseEntity.notFound().build();
         }
     }
}
