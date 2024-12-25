package com.example.linkly.repository;

import com.example.linkly.entity.Comment;
import com.example.linkly.exception.CommentException;
import com.example.linkly.exception.util.ErrorMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findByIdOrElseThrow(Long id) {
        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;
        return findById(id).orElseThrow(()-> new CommentException(errorMessage.getMessage(),errorMessage.getStatus()));
    }

    List<Comment> findTop5ByOrderByHeartCountDescCreatedAtAsc();

}
