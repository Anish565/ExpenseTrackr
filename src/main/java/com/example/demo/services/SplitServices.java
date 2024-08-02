package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entities.*;
import com.example.demo.repositories.SplitRepository;

public class SplitServices {
    
    @Autowired
    private SplitRepository splitRepository;

    public List<Split> findAllSplits() {
        List<Split> splits = (List<Split>)splitRepository.findAll();
        return splits;
    }

    public Optional<Split> findSplitById(Long id) {
        Optional<Split> split = splitRepository.findById(id);
        return split;
    }

    public Split saveSplit(Split split){
        return splitRepository.save(split);
    }

    public void deleteSplit(Split split){
        splitRepository.delete(split);
    }
    
}
