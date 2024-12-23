package com.example.linkly.entity;

import com.example.linkly.exception.util.ErrorMessage;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    private String id;

    @Column(length = 10)
    private String name;

    @Column(length = 30)
    private String email;

    @Column(length = 255)
    private String password;

    public User() {
    }
}
