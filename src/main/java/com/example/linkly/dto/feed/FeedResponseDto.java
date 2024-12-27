package com.example.linkly.dto.feed;

import com.example.linkly.entity.Feed;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedResponseDto {

    private final Long id;
    private final String userName;
    private final String title;
    private final String content;
    private final String imgUrl;
    private final Long heartCount;
    private final String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    public FeedResponseDto(Long id, String userName, String title, String imgUrl, String content, Long heartCount, LocalDateTime createdAt) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
        this.heartCount = heartCount;
        this.createdAt = createdAt;
        this.email=null;
    }

    public FeedResponseDto(Feed findFeed) {
        this.id = findFeed.getId();
        this.userName = findFeed.getUser().getName();
        this.title = findFeed.getTitle();
        this.imgUrl = findFeed.getImgUrl();
        this.content = findFeed.getContent();
        this.heartCount = findFeed.getHeartCount();
        this.createdAt = findFeed.getCreatedAt();
        this.email=findFeed.getUser().getEmail();
    }

    public static FeedResponseDto toDto(Feed feed) {
        return new FeedResponseDto(feed.getId(), feed.getUser().getName(), feed.getTitle(), feed.getImgUrl(), feed.getContent(), feed.getHeartCount(), feed.getCreatedAt());
    }

}
