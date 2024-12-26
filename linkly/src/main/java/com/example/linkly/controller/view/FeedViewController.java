package com.example.linkly.controller.view;

import com.example.linkly.dto.feed.CreateFeedRequestDto;
import com.example.linkly.dto.feed.FeedResponseDto;
import com.example.linkly.dto.feed.UpdateFeedRequestDto;
import com.example.linkly.entity.Feed;
import com.example.linkly.service.feed.FeedService;
import com.example.linkly.service.heart.HeartService;
import com.example.linkly.util.HeartCategory;
import com.example.linkly.util.auth.ValidatorUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/view/feed")
@RequiredArgsConstructor
public class FeedViewController {
    private final FeedService feedService;
    private final ValidatorUser validatorUser;
    private final HeartService heartService;

    @GetMapping
    public String displayFeedForm(Model model) {
        model.addAttribute("requestDto", new CreateFeedRequestDto());
        return "feed/addFeed";
    }

    @PostMapping()
    public String feedSave(@ModelAttribute CreateFeedRequestDto requestDto, BindingResult result, HttpServletRequest request, Model model) {
//        log.info("입력된 제목 ={} 내용 ={}",requestDto.getTitle(),requestDto.getContent());
        FeedResponseDto feedResponseDto = feedService.feedSave(requestDto,request);
        Long id = feedResponseDto.getId();
//        log.info("저장된 피드의 아이디 ={}", id);
        return "redirect:/view/feed/" + id;
    }

    @GetMapping("/{id}")
    public String displayFeedDetail(@PathVariable Long id, Model model, HttpServletRequest request) {
        String userEmail = validatorUser.getUserEmailFromTokenOrThrow(request);
        boolean isLiked = heartService.isILikeThis(id, HeartCategory.FEED, request);
//        log.info("로그인 한 유저의 email ={} ", userEmail);
        FeedResponseDto feed = feedService.findById(id);
//        log.info("해당 피드 작성한 유저 이메일 ={}", feed.getEmail());
        model.addAttribute("isLiked", isLiked);
        model.addAttribute("feed", feed);
        model.addAttribute("userEmail", userEmail);
        return "feed/feedDetail"; // 피드 상세 페이지 렌더링
    }

    @GetMapping("/edit-feed/{id}")
    public String displayFeedUpdateForm(@PathVariable Long id,Model model) {
        FeedResponseDto findFeed = feedService.findById(id);
        model.addAttribute("feed", findFeed);
        return "feed/editFeed";
    }

    @PostMapping("/edit-feed/{id}")
    public String updateFeed(@PathVariable Long id, @ModelAttribute UpdateFeedRequestDto responseDto) {
        log.info("수정 제목 : {}, 내용 : {} 이미지 : {}", responseDto.getTitle(), responseDto.getContent(),responseDto.getImgUrl());
        feedService.updateFeed(id,responseDto);
        return "redirect:/view/feed/" + id; // 업데이트 후 상세 페이지로 리다이렉트
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteFeed(@PathVariable Long id) {
        log.info("삭제 감지 id ={}",id);
        feedService.deleteFeed(id);
        return "redirect:/";
    }

    /**
     * 베스트5 피드 조회
     * @return
     */
    @GetMapping("/best")
    public String getBestFeeds(Model model) {
        List<Feed> bestFeeds = feedService.getBestFeeds();
        model.addAttribute("feeds", bestFeeds);
        return "feed/bestFeed";
    }
}
