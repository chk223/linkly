package com.example.linkly.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(length = 10)
    private String name;

    @Column(length = 30)
    private String email;

    @Column(length = 255)
    private String password;

    @OneToMany(mappedBy = "follower")
    private List<Friend> followers = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Friend> followings = new ArrayList<>();

    public User() {
    }
}
