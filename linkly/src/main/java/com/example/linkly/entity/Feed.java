package com.example.linkly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "feed")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 40)
    private String title;

    @Setter
    @Column(length = 100)
    private String content;

    @Setter
    @Column(name = "img_url")
    private String imgUrl;
    @Column(name = "like_count")
    private Long heartCount;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Feed() {
    }

    public Feed(String title, String imgUrl, String content, Long heartCount) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
        this.heartCount = heartCount;
    }

    public Long increaseCount () {
        this.heartCount += 1;
        return this.heartCount;
    }

    public Long decreaseCount () {
        this.heartCount -= 1;
        return this.heartCount;
    }

}
