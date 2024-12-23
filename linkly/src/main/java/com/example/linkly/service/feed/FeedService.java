package com.example.linkly.service.feed;

import com.example.linkly.dto.feed.FeedResponseDto;
import com.example.linkly.dto.feed.UpdateFeedRequestDto;
import com.example.linkly.entity.Feed;
import org.springframework.data.domain.Page;

public interface FeedService {
    FeedResponseDto feedSave(String userId, String title, String imgUrl, String content);

    FeedResponseDto findById(Long id);

    FeedResponseDto updateFeed(Long id, UpdateFeedRequestDto requestDto);

    void deleteFeed(Long id);

    Page<Feed> getFeedsPagination(int page, int size);
}
