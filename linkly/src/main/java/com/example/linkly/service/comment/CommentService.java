package com.example.linkly.service.comment;

import com.example.linkly.dto.comment.CommentResponseDto;

public interface CommentService {

    CommentResponseDto addComment(String contents, Long feedId);

    CommentResponseDto findFeedById(Long feedId);

    CommentResponseDto update(Long id, String content, String userId);


    void delete(Long id , String commentId);



}
