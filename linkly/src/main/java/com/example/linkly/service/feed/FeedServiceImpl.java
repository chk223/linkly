package com.example.linkly.service.feed;

import com.example.linkly.dto.feed.FeedResponseDto;
import com.example.linkly.entity.Feed;
import com.example.linkly.entity.User;
import com.example.linkly.repository.FeedRepository;
import com.example.linkly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService{

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Override
    public FeedResponseDto feedSave(String userId, String imgUrl, String content) {
//        User findUser = UserRepository.findById(userId).orElseThrow(()-> throw new UserException());
        Feed feed = new Feed(imgUrl, content, 0L);
        User findUser = new User();  //임시 유저 머지 후 삭제
        feed.setUser(findUser);
//        log.info("컨텐츠={}, 생성일={}, feedid={}, 유저={}, 라이크={}, url={}" ,feed.getContent(), feed.getCreatedAt(), feed.getId(), feed.getUser(), feed.getHeartCount(), feed.getImgUrl());
        Feed saveFeed = feedRepository.save(feed);
//        log.info("저장된id={}, 저장된유저={}, 저장된컨텐츠={}, 저장된url={}, 저장된카운트={}, 저장된날짜={}", saveFeed.getId(), saveFeed.getUser(), saveFeed.getContent(), saveFeed.getImgUrl(), saveFeed.getHeartCount(), saveFeed.getCreatedAt());
        return new FeedResponseDto(saveFeed.getId(), saveFeed.getUser(), saveFeed.getContent(), saveFeed.getImgUrl(), saveFeed.getHeartCount(), saveFeed.getCreatedAt());
    }
}
