package com.example.linkly.repository;

import com.example.linkly.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    /**
     * 페이징을 랜덤으로 쿼리에서 처리할 수 있게 작성
     * @param pageable
     * @return
     */
    @Query("SELECT f FROM Feed f ORDER BY function('RAND')")
    Page<Feed> findAllRandom(Pageable pageable);

    List<Feed> findTop5ByOrderByHeartCountDesc();
}
