package com.example.linkly.service.feed;

import com.example.linkly.dto.feed.FeedResponseDto;
import com.example.linkly.dto.feed.UpdateFeedRequestDto;
import com.example.linkly.entity.Feed;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FeedService {
    FeedResponseDto feedSave(UUID userId, String title, String imgUrl, String content);

    FeedResponseDto findById(Long id);

    FeedResponseDto updateFeed(Long id, UpdateFeedRequestDto requestDto);

    void deleteFeed(Long id);

    Page<Feed> getFeedsPagination(int page, int size);

    List<Feed> getBestFeeds();
}
