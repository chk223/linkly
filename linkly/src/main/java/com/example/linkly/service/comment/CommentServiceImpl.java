package com.example.linkly.service.comment;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
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
        Comment comment = commentRepository.save(new Comment(contents,user,feed,0L));

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

    /**
     * 댓글 수정
     * @param id
     * @param content
     * @param userId
     * @return
     */
    @Override
    public CommentResponseDto update(Long id, String content, UUID userId) {

        Comment updateComment = commentRepository.findByIdOrElseThrow(id);

        updateComment.update(content);

        return CommentResponseDto.toDto(updateComment);

    }

    /**
     * 댓글 삭제
     * @param id
     */
    @Override
    public void delete(Long id) {

        Comment comment = commentRepository.findByIdOrElseThrow(id);

        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> heartCountNumber() {

        return commentRepository.findTop5ByOrderByHeartCountDescCreatedAtAsc();
    }


}
