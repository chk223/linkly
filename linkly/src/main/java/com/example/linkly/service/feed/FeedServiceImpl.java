package com.example.linkly.service.feed;

import com.example.linkly.dto.feed.CreateFeedRequestDto;
import com.example.linkly.dto.feed.FeedResponseDto;
import com.example.linkly.dto.feed.UpdateFeedRequestDto;
import com.example.linkly.entity.Feed;
import com.example.linkly.entity.User;
import com.example.linkly.exception.AuthException;
import com.example.linkly.exception.FeedException;
import com.example.linkly.exception.UserException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.FeedRepository;
import com.example.linkly.repository.UserRepository;
import com.example.linkly.util.auth.JwtUtil;
import com.example.linkly.util.auth.ValidatorUser;
import com.example.linkly.util.exception.ExceptionUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final ValidatorUser validatorUser;

    @Override
    public FeedResponseDto feedSave(CreateFeedRequestDto requestDto, HttpServletRequest request) {
//        log.info("피드 저장 시도");
        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;
        String userEmail = validatorUser.getUserEmailFromTokenOrThrow(request);
//        log.info("저장하려는 유저의 이메일 ={}",userEmail);
        User findUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserException(errorMessage.getMessage(), errorMessage.getStatus()));
        Feed feed = new Feed(requestDto.getTitle(), Objects.equals(requestDto.getImgUrl(), "") ? null : requestDto.getImgUrl(), requestDto.getContent(), 0L);
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
        if (requestDto.getImgUrl() != null) {
            findFeed.setContent(requestDto.getImgUrl());
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
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());

        return feedRepository.findAll(pageable);
    }


}
