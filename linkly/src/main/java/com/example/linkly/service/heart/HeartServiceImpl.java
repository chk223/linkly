package com.example.linkly.service.heart;

import com.example.linkly.entity.Feed;
import com.example.linkly.entity.Heart;
import com.example.linkly.entity.User;
import com.example.linkly.exception.CommentException;
import com.example.linkly.exception.FeedException;
import com.example.linkly.exception.UserException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.CommentRepository;
import com.example.linkly.repository.FeedRepository;
import com.example.linkly.repository.HeartRepository;
import com.example.linkly.repository.UserRepository;
import com.example.linkly.util.HeartCategory;
import lombok.RequiredArgsConstructor;
import org.attoparser.dom.Comment;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
//    private final CommentRepository commentRepository;

    /**
     * 좋아요 토글
     * @param userId
     * @param categoryId
     * @param category
     * @return
     */
    @Override
    public String toggleHeart(UUID userId, Long categoryId, HeartCategory category) {

        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;

        // user 조회
        User user = userRepository.findById(userId).orElseThrow(()->
           new UserException(errorMessage.getMessage(), errorMessage.getStatus()));

        // 좋아요 여부 확인
        Optional<Heart> heartOptional = heartRepository.findByUserAndCategoryIdAndCategory(user, categoryId, category);

        if (category == HeartCategory.FEED) {
            Feed findFeed = feedRepository.findById(categoryId).orElseThrow(() ->
                    new FeedException(errorMessage.getMessage(), errorMessage.getStatus()));

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
            Comment findComment = commentRepository.finById(categoryId).orElseThrow(() ->
                    new CommentException(errorMessage.getMessage(), errorMessage.getStatus()));

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

}
