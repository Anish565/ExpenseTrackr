package com.example.demo.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "groups")
@Getter
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User admin;

    @ManyToMany
    @JoinTable(
            name = "group_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Split> splits;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Split> settlements;

    // Constructors, getters, and setters

    public Group() {

    }


    public Group(String name, Date date, User admin, List<User> users) {
        this.name = name;
        this.date = date;
        this.admin = admin;
        this.users = users;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public User getAdmin() {
        return admin;
    }


    public void setAdmin(User admin) {
        this.admin = admin;
    }


    public List<User> getUsers() {
        return users;
    }


    public void setUsers(List<User> users) {
        this.users = users;
    }


    // Additional methods

    @Override
    public String toString() {
        return "Group [id=" + id + ", name=" + name + ", date=" + date + ", admin=" + admin + ", users=" + users
                + "]";
    }



}
