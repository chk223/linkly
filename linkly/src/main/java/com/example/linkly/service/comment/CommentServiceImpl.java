package com.example.linkly.service.comment;

import com.example.linkly.dto.comment.CommentRequestDto;
import com.example.linkly.dto.comment.CommentResponseDto;
import com.example.linkly.entity.Comment;
import com.example.linkly.entity.Feed;
import com.example.linkly.entity.User;
import com.example.linkly.exception.FeedException;
import com.example.linkly.exception.UserException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.CommentRepository;
import com.example.linkly.repository.FeedRepository;
import com.example.linkly.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final HttpSession session;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;

    /**
     * 댓글 생성
     * @param contents
     * @param feedId
     * @return
     */
    @Override
    public CommentResponseDto addComment(UUID id, String contents, Long feedId) {

        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;

        User user = userRepository.findById(id).orElseThrow(()-> new UserException(errorMessage.getMessage(),errorMessage.getStatus()));;
        Feed feed = feedRepository.findById(feedId).orElseThrow(()-> new FeedException(errorMessage.getMessage(),errorMessage.getStatus()));
        Comment comment = commentRepository.save(new Comment(contents,user,feed));

        return CommentResponseDto.toDto(comment);
    }

    /**
     * 특정 피드 리스트
     * @param feedId
     * @return
     */
    @Override
    public CommentResponseDto findCommentFeedById(Long feedId) {

        Comment comment = commentRepository.findByIdOrElseThrow(feedId);
        return CommentResponseDto.toDto(comment);
    }

    @Override
    public CommentResponseDto update(Long id, String content, UUID userId) {

        Comment updateComment = commentRepository.findByIdOrElseThrow(id);

        updateComment.update(content);

        return CommentResponseDto.toDto(updateComment);

    }

    @Override
    public void delete(Long id) {

        Comment comment = commentRepository.findByIdOrElseThrow(id);

        commentRepository.delete(comment);
    }



}
