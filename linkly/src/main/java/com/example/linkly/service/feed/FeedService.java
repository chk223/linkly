package com.example.linkly.service.feed;

import com.example.linkly.dto.feed.FeedResponseDto;
import com.example.linkly.dto.feed.UpdateFeedRequestDto;
import jakarta.validation.Valid;

public interface FeedService {
    FeedResponseDto feedSave(String userId, String title, String imgUrl, String content);

    FeedResponseDto findById(Long id);

    FeedResponseDto updateFeed(Long id, UpdateFeedRequestDto requestDto);

    void deleteFeed(Long id);
}
