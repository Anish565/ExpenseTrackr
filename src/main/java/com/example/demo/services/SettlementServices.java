package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repositories.SettlementRepository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.*;

public class SettlementServices {
    
    @Autowired
    private SettlementRepository settlementRepository;

    public List<Settlements> findAllSettlements() {
        List<Settlements> settlements = (List<Settlements>)settlementRepository.findAll();
        return settlements;
    }

    public Optional<Settlements> findSettlementsById(Long id) {
        Optional<Settlements> settlements = settlementRepository.findById(id);
        return settlements;
    }

    public Settlements savSettlements(Settlements settlements) {
        Settlements settlement = settlementRepository.save(settlements);
        return settlement;
    }

    public void deleteSettlements(Settlements settlements) {
        settlementRepository.delete(settlements);
    }
}
