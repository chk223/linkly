package com.example.linkly.service.heart;

import com.example.linkly.util.HeartCategory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface HeartService {
    String toggleHeart(Long categoryId, HeartCategory category, HttpServletRequest request);
    boolean isILikeThis(Long categoryId, HeartCategory category, HttpServletRequest request);
}
