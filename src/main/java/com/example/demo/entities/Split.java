package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "splits")
@Data
@Getter
public class Split {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    private User payee;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private int amount;

    private boolean settled;

    // Constructors, getters, and setters
    public Split() {

    }
    
    public Split(Group group, User payee, User payer, Category category, int amount) {  
        this.group = group;
        this.payee = payee;
        this.payer = payer;
        this.category = category;
        this.amount = amount;
        this.settled = false;
    }


    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getPayee() {
        return payee;
    }

    public void setPayee(User payee) {
        this.payee = payee;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    // Other methods

    @Override
    public String toString() {
        return "Split{" +
                "id=" + id +
                ", group=" + group +
                ", payee=" + payee +
                ", payer=" + payer +
                ", category=" + category +
                ", amount=" + amount +
                ", settled=" + settled +
                '}';
    }   



}
