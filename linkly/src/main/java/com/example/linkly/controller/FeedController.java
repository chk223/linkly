package com.example.linkly.controller;


import com.example.linkly.dto.feed.CreateFeedRequestDto;
import com.example.linkly.dto.feed.FeedResponseDto;
import com.example.linkly.service.feed.FeedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping
    public ResponseEntity<FeedResponseDto> feedSave(@Valid @RequestBody CreateFeedRequestDto requestDto) {
        FeedResponseDto feedResponseDto = feedService.feedSave(requestDto.getUserId(), requestDto.getImgUrl(), requestDto.getContent());
        return new ResponseEntity<>(feedResponseDto, HttpStatus.CREATED);
    }
}
