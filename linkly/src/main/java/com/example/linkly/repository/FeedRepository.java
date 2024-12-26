package com.example.linkly.repository;

import com.example.linkly.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    /**
     * 페이징을 랜덤으로 쿼리에서 처리할 수 있게 작성
     * @param pageable
     * @return
     */
    @Query("SELECT f FROM Feed f ORDER BY function('RAND')")
    Page<Feed> findAllRandom(Pageable pageable);

    // 베스트5피드를 좋아요 수와 생성날짜 내림차순
    List<Feed> findTop5ByOrderByHeartCountDescCreatedAtAsc();


    // 친구 피드 보기
    @Query(value = "select * from feed f where user_id in (select following from friend where follower = :userId)" +
            "ORDER BY f.created_at DESC " +
            "LIMIT :offset, :pageSize",
    nativeQuery = true)
    List<Feed> findFriendFeeds(
            @Param("userId") UUID userId,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize);
}
