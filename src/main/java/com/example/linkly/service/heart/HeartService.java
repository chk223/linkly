package com.example.linkly.service.heart;

import com.example.linkly.common.util.HeartCategory;
import jakarta.servlet.http.HttpServletRequest;

public interface HeartService {
    String toggleHeart(Long categoryId, HeartCategory category, HttpServletRequest request);
    boolean isILikeThis(Long categoryId, HeartCategory category, HttpServletRequest request);
}
