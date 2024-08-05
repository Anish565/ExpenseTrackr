package com.example.demo.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy="category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Expense> expenses;

    @OneToMany(mappedBy="category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Split> splits;


    // only getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<Split> getSplits() {
        return splits;
    }
}
