package com.example.linkly.repository;

import com.example.linkly.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query("SELECT f FROM Feed f ORDER BY function('RAND')")
    Page<Feed> findAllRandom(Pageable pageable);
}
