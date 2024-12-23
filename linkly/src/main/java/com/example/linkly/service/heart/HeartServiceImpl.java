package com.example.linkly.service.heart;

import com.example.linkly.entity.Feed;
import com.example.linkly.entity.Heart;
import com.example.linkly.entity.User;
import com.example.linkly.exception.FeedException;
import com.example.linkly.exception.UserException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.FeedRepository;
import com.example.linkly.repository.HeartRepository;
import com.example.linkly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;

    @Override
    public String toggleHeart(String userId, Long feedId) {

        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;

        User user = userRepository.findById(userId).orElseThrow(()->
           new UserException(errorMessage.getMessage(), errorMessage.getStatus()));

        Feed feed = feedRepository.findById(feedId).orElseThrow(() ->
                new FeedException(errorMessage.getMessage(), errorMessage.getStatus()));

        // 좋아요 여부 확인
        Optional<Heart> heartOptional = heartRepository.findByUserIdAndFeedId(user, feed);
        if (heartOptional.isPresent()) {
            //좋아요가 되어 있다면 삭제
            heartRepository.delete(heartOptional.get());
            return "Heart removed";
        } else {
            // 좋아요가 되어 있지 않다면 생성
            Heart heart = new Heart();
            heart.setUser(user);
            heart.setFeed(feed);

            heartRepository.save(heart);
            return "heart added";
        }
    }
}
