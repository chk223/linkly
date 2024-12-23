package com.example.linkly.service.feed;

import com.example.linkly.dto.feed.FeedResponseDto;
import com.example.linkly.dto.feed.UpdateFeedRequestDto;
import com.example.linkly.entity.Feed;
import com.example.linkly.entity.User;
import com.example.linkly.exception.FeedException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.FeedRepository;
import com.example.linkly.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Override
    public FeedResponseDto feedSave(String userId, String title, String imgUrl, String content) {
        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;
//        User findUser = UserRepository.findById(userId).orElseThrow(()-> throw new UserException(errorMessage.getMessage(), errorMessage.getStatus()));
        Feed feed = new Feed(title, imgUrl, content, 0L);
        User findUser = new User();  //임시 유저 머지 후 삭제
        feed.setUser(findUser);
//        log.info("컨텐츠={}, 생성일={}, feedid={}, 유저={}, 라이크={}, url={}" ,feed.getContent(), feed.getCreatedAt(), feed.getId(), feed.getUser(), feed.getHeartCount(), feed.getImgUrl());
        Feed saveFeed = feedRepository.save(feed);
//        log.info("저장된id={}, 저장된유저={}, 저장된컨텐츠={}, 저장된url={}, 저장된카운트={}, 저장된날짜={}", saveFeed.getId(), saveFeed.getUser(), saveFeed.getContent(), saveFeed.getImgUrl(), saveFeed.getHeartCount(), saveFeed.getCreatedAt());
        return new FeedResponseDto(saveFeed);
    }

    /**
     * 피드 단건 조회
     *
     * @param id
     * @return
     */
    @Override
    public FeedResponseDto findById(Long id) {
        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;
        Feed findFeed = feedRepository.findById(id).orElseThrow(() -> new FeedException(errorMessage.getMessage(), errorMessage.getStatus()));

        return new FeedResponseDto(findFeed);
    }


    /**
     * 피드 수정
     * @param id
     * @param requestDto
     * @return
     */
    @Override
    public FeedResponseDto updateFeed(Long id, UpdateFeedRequestDto requestDto) {
        ErrorMessage errorNotFound = ErrorMessage.ENTITY_NOT_FOUND;
        ErrorMessage errorBad = ErrorMessage.BLANK_INPUT;
        Feed findFeed = feedRepository.findById(id).orElseThrow(() -> new FeedException(errorNotFound.getMessage(), errorNotFound.getStatus()));

//        if (!findFeed.getUser().getName().equals(requestDto.getUserName())) {
//            throw new FeedException("작성자 이름이 다릅니다.", HttpStatus.UNAUTHORIZED);
//        }

        if (requestDto.getTitle() == null && requestDto.getContent() == null) {
            throw new FeedException(errorBad.getMessage(), errorBad.getStatus());
        }

        if (requestDto.getTitle() != null) {
            findFeed.setTitle(requestDto.getTitle());
        }
        if (requestDto.getContent() != null) {
            findFeed.setContent(requestDto.getContent());
        }
        feedRepository.save(findFeed);
        return FeedResponseDto.toDto(findFeed);
    }

    /**
     * 피드 삭제
     * @param id
     */
    @Override
    public void deleteFeed(Long id) {
        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;
        Feed findFeed = feedRepository.findById(id).orElseThrow(() -> new FeedException(errorMessage.getMessage(), errorMessage.getStatus()));
        feedRepository.delete(findFeed);
    }

}
