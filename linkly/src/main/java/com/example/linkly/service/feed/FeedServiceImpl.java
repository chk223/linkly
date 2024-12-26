package com.example.linkly.service.feed;

import com.example.linkly.dto.feed.CreateFeedRequestDto;
import com.example.linkly.dto.feed.FeedResponseDto;
import com.example.linkly.dto.feed.UpdateFeedRequestDto;
import com.example.linkly.entity.Feed;
import com.example.linkly.entity.User;
import com.example.linkly.exception.FeedException;
import com.example.linkly.exception.UserException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.FeedRepository;
import com.example.linkly.repository.UserRepository;
import jakarta.transaction.Transactional;
import com.example.linkly.util.auth.ValidatorUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final ValidatorUser validatorUser;

    /**
     * 피드 생성
     * @param requestDto
     * @param request
     * @return
     */
    @Override
    public FeedResponseDto feedSave(CreateFeedRequestDto requestDto, HttpServletRequest request) {
//        log.info("피드 저장 시도");
        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;
        String userEmail = validatorUser.getUserEmailFromTokenOrThrow(request);
//        log.info("저장하려는 유저의 이메일 ={}",userEmail);
        User findUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserException(errorMessage.getMessage(), errorMessage.getStatus()));
        Feed feed = new Feed(requestDto.getTitle(), Objects.equals(requestDto.getImgUrl(), "") ? null : requestDto.getImgUrl(), requestDto.getContent(), 0L);
        ;

        feed.setUser(findUser);
        Feed saveFeed = feedRepository.save(feed);
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
    @Transactional
    public FeedResponseDto updateFeed(Long id, UpdateFeedRequestDto requestDto) {
        ErrorMessage errorNotFound = ErrorMessage.ENTITY_NOT_FOUND;
        ErrorMessage errorBad = ErrorMessage.BLANK_INPUT;
        Feed findFeed = feedRepository.findById(id).orElseThrow(() -> new FeedException(errorNotFound.getMessage(), errorNotFound.getStatus()));


        // 피드수정 요청에 아무 값이 들어오지 않았을 경우 에러 처리
        if (requestDto.getTitle() == null && requestDto.getContent() == null) {
            throw new FeedException(errorBad.getMessage(), errorBad.getStatus());
        }

        // 피드에 수정할 content가 없다면 제외시키기
        if (requestDto.getTitle() != null) {
            findFeed.setTitle(requestDto.getTitle());
        }
        // 피드에 수정할 title이 없다면 제외시키기
        if (requestDto.getContent() != null) {
            findFeed.setContent(requestDto.getContent());
        }
        // 피드에 수정할 URL이 없다면 제외시키기
        if (requestDto.getImgUrl() != null) {
            findFeed.setImgUrl(requestDto.getImgUrl());
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

    /**
     * 피드 페이징
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Feed> getFeedsPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return feedRepository.findAllRandom(pageable);
    }

    /**
     * 베스트5 피드 조회
     * @return
     */
    @Override
    public List<Feed> getBestFeeds() {
        return feedRepository.findTop5ByOrderByHeartCountDescCreatedAtAsc();
    }

    /**
     * 친구 피드 보기
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<Feed> getFriendFeeds(UUID userId, int page, int size) {
        int offset = page * size;
        return feedRepository.findFriendFeeds(userId, offset, size);
    }

}
