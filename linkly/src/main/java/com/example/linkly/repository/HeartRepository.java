package com.example.linkly.repository;

import com.example.linkly.entity.Heart;
import com.example.linkly.entity.User;
import com.example.linkly.util.HeartCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    //ID와 카테고리를 조합하여 조회할 수 있게 쿼리 작성
    Optional<Heart> findByUserAndCategoryIdAndCategory(User user, Long categoryId, HeartCategory category);
    int countByCategoryIdAndCategory(Long categoryId, HeartCategory category);
}

