package com.example.linkly.entity;

import com.example.linkly.util.HeartCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "heart")
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @Enumerated(EnumType.STRING)
    @Setter
    private HeartCategory category;

    @Setter
    private Long categoryId;

    public Heart() {
    }
}
