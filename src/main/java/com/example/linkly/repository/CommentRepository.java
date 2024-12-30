package com.example.linkly.repository;

import com.example.linkly.entity.Comment;
import com.example.linkly.common.exception.ApiException;
import com.example.linkly.common.exception.util.ErrorMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findByIdOrElseThrow(Long id) {
        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;
        return findById(id).orElseThrow(()-> new ApiException(errorMessage.getMessage(),errorMessage.getStatus()));
    }

    List<Comment> findAllByFeedId(Long feedId);

    List<Comment> findTop5ByOrderByHeartCountDescCreatedAtAsc();

}
