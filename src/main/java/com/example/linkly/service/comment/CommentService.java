package com.example.linkly.service.comment;

import com.example.linkly.common.dto.comment.CommentResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CommentService {

    CommentResponseDto addComment(String contents, Long feedId, HttpServletRequest request);

    CommentResponseDto findCommentFeedById(Long feedId);

    List<CommentResponseDto> findAllCommentFromFeed(Long feedId);

    CommentResponseDto update(Long id, String content);

    void delete(Long id);


    List<CommentResponseDto> heartCountNumber();
}
