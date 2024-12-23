package com.example.linkly.entity;

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

        @ManyToOne
        @JoinColumn(name = "feed_id")
        @Setter
        private Feed feed;

        public Heart() {
        }
}
