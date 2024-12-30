package com.example.linkly.controller;

import com.example.linkly.service.heart.HeartService;
import com.example.linkly.common.util.HeartCategory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/hearts")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    @PostMapping("/toggle")
    public ResponseEntity<String> toggleHeart(
            @RequestParam Long categoryId,
            @RequestParam HeartCategory category,
            HttpServletRequest request
            ) {
        String result = heartService.toggleHeart(categoryId, category, request);

        return ResponseEntity.ok(result);
    }
}
