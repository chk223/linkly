package com.example.linkly.repository;

import com.example.linkly.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
//    //나를 팔로우하는 사람 조회하기
//    List<Friend> findByFollowerId(String followerId);
//
//    //내가 팔로우하는 사람 조회하기
//    List<Friend> findByFollowingId(String followingId);
}


