package com.example.linkly.service.feed;

import com.example.linkly.dto.feed.FeedResponseDto;

public interface FeedService {
    FeedResponseDto feedSave(String userId, String imgUrl, String content);
}
