package com.example.linkly.service.comment;

import com.example.linkly.dto.comment.CommentResponseDto;
import com.example.linkly.entity.Comment;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    CommentResponseDto addComment(String contents, Long feedId, HttpServletRequest request);

    CommentResponseDto findCommentFeedById(Long feedId);

    List<CommentResponseDto> findAllCommentFromFeed(Long feedId);

    CommentResponseDto update(Long id, String content);

    void delete(Long id);


    List<CommentResponseDto> heartCountNumber();
}
