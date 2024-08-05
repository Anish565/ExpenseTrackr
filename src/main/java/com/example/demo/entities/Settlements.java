package com.example.demo.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "settlements")
@Data
@Getter
public class Settlements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    // @JsonIgnore
    private Group group;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private int amount;

    private Date settledDate;

    public Settlements() {
    }

    public Settlements(Group group, User payer, User receiver, int amount, Date settledDate) {
        this.group = group;
        this.payer = payer;
        this.receiver = receiver;
        this.amount = amount;
        this.settledDate = settledDate;
    }

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

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {   
        this.receiver = receiver;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getSettledDate() {
        return settledDate;
    }

    public void setSettledDate(Date settledDate) {
        this.settledDate = settledDate;
    }

    @Override
    public String toString() {
        return "Settlements [id=" + id + ", group=" + group + ", payer=" + payer + ", receiver=" + receiver
                + ", amount=" + amount + ", settledDate=" + settledDate + "]";
    }
    
}
