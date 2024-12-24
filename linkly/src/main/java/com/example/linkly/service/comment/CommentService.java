package com.example.linkly.service.comment;

import com.example.linkly.dto.comment.CommentResponseDto;
import com.example.linkly.entity.User;

import java.util.UUID;

public interface CommentService {

    CommentResponseDto addComment(UUID id, String contents, Long feedId);

    CommentResponseDto findCommentFeedById(Long feedId);

    CommentResponseDto update(Long id, String content, UUID userId);

    void delete(Long id);



}
