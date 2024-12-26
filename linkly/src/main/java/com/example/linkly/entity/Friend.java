package com.example.linkly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "friend")
@EntityListeners(AuditingEntityListener.class)
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower", nullable = false)//누가
    private User follower; // 나를 팔로우 하는 사용자 (상대가 나에게 팔로우 요청)

    @ManyToOne
    @JoinColumn(name = "following", nullable = false)//누구에게
    private User following; // 내가 팔로우 하는 사용자 (내가 상대에게 팔로우 요청)

    @CreatedDate
    @Column(name = "create_at", updatable = false)
    private LocalDateTime createdAt;

    public Friend() {
    }

    public Friend(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }

}
