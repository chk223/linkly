package com.example.linkly.service.heart;

import com.example.linkly.util.HeartCategory;

import java.util.UUID;

public interface HeartService {
    String toggleHeart(UUID user, Long categoryId, HeartCategory category);

}
