package com.example.linkly.repository;

import com.example.linkly.entity.Friend;
import com.example.linkly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    //나를 팔로우하는 사람 조회하기
    List<Friend> findByFollower(User follower);

    //내가 팔로우하는 사람 조회하기
//    List<Friend> findByFollowing(User following);
    @Query("SELECT f FROM Friend f WHERE f.follower = :follower")
    List<Friend> findByFollowing(@Param("follower") User follower);


    @Modifying
    @Query("DELETE FROM Friend f WHERE f.follower = :follower AND f.following = :following")
    void deleteByFollowerAndFollowing(@Param("follower") User follower, @Param("following") User following);

}


