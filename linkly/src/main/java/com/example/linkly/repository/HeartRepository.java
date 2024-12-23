package com.example.linkly.repository;

import com.example.linkly.entity.Feed;
import com.example.linkly.entity.Heart;
import com.example.linkly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
//    Optional<Heart> findByUserIdAndFeedId(User userId, Feed feedId);
//    int countByFeedId(Feed feedId);
}
