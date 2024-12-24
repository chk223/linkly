package com.example.linkly.entity;

import com.example.linkly.dto.comment.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @Column(name = "like_count")
    private Long heartCount;

    public Comment() {
    }

    public Comment(String content, User user, Feed feed, Long heartCount) {
        this.content = content;
        this.user = user;
        this.feed = feed;
        this.heartCount = heartCount;
    }

    public void update(String content) {
        this.content = content;
    }

    // 좋아요개수 증가
    public Long increaseCount () {
        this.heartCount += 1;
        return this.heartCount;
    }

    // 좋아요개수 다운
    public Long decreaseCount() {
        this.heartCount -= 1;
        return this.heartCount;
    }
}
