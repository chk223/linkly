package com.example.linkly.service.heart;

import com.example.linkly.entity.Comment;
import com.example.linkly.entity.Feed;
import com.example.linkly.entity.Heart;
import com.example.linkly.entity.User;
import com.example.linkly.common.exception.ApiException;
import com.example.linkly.common.exception.util.ErrorMessage;
import com.example.linkly.repository.CommentRepository;
import com.example.linkly.repository.FeedRepository;
import com.example.linkly.repository.HeartRepository;
import com.example.linkly.repository.UserRepository;
import com.example.linkly.common.util.HeartCategory;
import com.example.linkly.common.util.auth.ValidatorUser;
import com.example.linkly.common.exception.ExceptionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final ValidatorUser validatorUser;


    /**
     * 좋아요 토글
     *
     * @param
     * @param categoryId
     * @param category
     * @return
     */
    @Override
    @Transactional
    public String toggleHeart(Long categoryId, HeartCategory category, HttpServletRequest request) {

        String userEmail = validatorUser.getUserEmailFromTokenOrThrow(request);
        // user 조회
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.ENTITY_NOT_FOUND, ApiException.class));
        // 좋아요 여부 확인
        Optional<Heart> heartOptional = heartRepository.findByUserAndCategoryIdAndCategory(user, categoryId, category);

        if (category == HeartCategory.FEED) {
            Feed findFeed = feedRepository.findById(categoryId).orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.ENTITY_NOT_FOUND, ApiException.class));
            if (heartOptional.isPresent()) {
                //좋아요가 되어 있다면 삭제
                findFeed.decreaseCount();
                heartRepository.delete(heartOptional.get());

                return "Heart removed";
            } else {
                // 좋아요가 되어 있지 않다면 생성
                Heart heart = new Heart();
                heart.setUser(user);
                heart.setCategoryId(categoryId);
                heart.setCategory(category);
                findFeed.increaseCount();

                heartRepository.save(heart);
                return "heart added";
            }
        } else if (category == HeartCategory.COMMENT) {
            Comment findComment = commentRepository.findById(categoryId).orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.ENTITY_NOT_FOUND, ApiException.class));
            if (heartOptional.isPresent()) {
                findComment.decreaseCount();
                heartRepository.delete(heartOptional.get());
                return "Heart removed";
            } else {
                Heart heart = new Heart();
                heart.setUser(user);
                heart.setCategoryId(categoryId);
                heart.setCategory(category);
                findComment.increaseCount();

                heartRepository.save(heart);
                return "heart added";
            }
        }
        return null;

    }

    @Override
    public boolean isILikeThis(Long categoryId, HeartCategory category, HttpServletRequest request) {
        String userEmail = validatorUser.getUserEmailFromTokenOrThrow(request);
        // user 조회
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.ENTITY_NOT_FOUND, ApiException.class));
        // 좋아요 여부 확인
        Optional<Heart> heartOptional = heartRepository.findByUserAndCategoryIdAndCategory(user, categoryId, category);
        return heartOptional.isPresent();
    }
}

